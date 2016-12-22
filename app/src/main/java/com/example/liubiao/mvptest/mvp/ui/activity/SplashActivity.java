package com.example.liubiao.mvptest.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.liubiao.mvptext.R;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by liubiao on 2016/12/2.
 */

public class SplashActivity extends AppCompatActivity {


    ImageView ivOut;
    ImageView ivInner;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.animator.fade_in,android.R.animator.fade_out);
        setContentView(R.layout.activity_splash);
        ivInner= (ImageView) findViewById(R.id.iv_inner);
        ivOut= (ImageView) findViewById(R.id.iv_out);
        tv= (TextView) findViewById(R.id.tv);
        tv.setAlpha(0);
        initAnimator();
    }

    private void initAnimator() {
        //内部向上运动
         Animation animation = AnimationUtils.loadAnimation(this, R.anim.iv_inner_up);
        ivInner.startAnimation(animation);
        //当内部的运动带到80%时外部运动
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private boolean flag=false;
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (valueAnimator.getAnimatedFraction()>=0.8 &&!flag)
                {
                    flag=true;
                    //外部开始运动
                    YoYo.with(Techniques.RubberBand).duration(2000).playOn(ivOut);
                    YoYo.with(Techniques.FadeIn).duration(2000).playOn(tv);
                }
                if(valueAnimator.getAnimatedFraction()>=0.95)
                {
                    valueAnimator.cancel();
                    YoYo.with(Techniques.Bounce).duration(2000).playOn(ivInner);
                    finishAct();
                }
            }
        });
        valueAnimator.start();
    }
    private void finishAct() {
        rx.Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                     @Override
                     public void call(Long aLong) {
                         Intent intent = new Intent(SplashActivity.this, NewsActivity.class);
                         SplashActivity.this.startActivity(intent);
                          SplashActivity.this.finish();

                     }
        });

    }

}
