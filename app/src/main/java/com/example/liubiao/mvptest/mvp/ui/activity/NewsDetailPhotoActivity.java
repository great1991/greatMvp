package com.example.liubiao.mvptest.mvp.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.liubiao.mvptest.event.NewsPhotoEvent;
import com.example.liubiao.mvptest.mvp.entity.NewsDetailPhotoBean;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptest.mvp.ui.adapter.PagerAdapter.NewsPhotoPagerAdapter;
import com.example.liubiao.mvptest.utils.RxBus;
import com.example.liubiao.mvptext.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by liubiao on 2016/12/20.
 */
public class NewsDetailPhotoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv)
    TextView tv;
    private List<String> piclist = new ArrayList<>();
    private List<String> titlelist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getRxBusInstace().toObservable(NewsPhotoEvent.class).subscribe(new Action1<NewsPhotoEvent>() {
            @Override
            public void call(NewsPhotoEvent newsPhotoEvent) {
                startAnimation();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startAnimation() {
        float alpha = tv.getAlpha();
        float value0=1.0f;
        float value1=0.0f;
        if (alpha==9)
        {
            value0=0.0f;
            value1=1.0f;
        }
        ObjectAnimator anim = ObjectAnimator.ofFloat(tv, "xx", value0, value1).setDuration(500);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                tv.setAlpha(value);
            }
        });
    }

    @Override
    public void initInject() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details_photo;
    }

    @Override
    protected void initView() {
        NewsDetailPhotoBean bean = getIntent().getParcelableExtra("bean");
        List<NewsDetailPhotoBean.Picture> pictures = bean.getPictures();
        if (pictures == null || pictures.size() == 0) {
            return;
        }
        for (NewsDetailPhotoBean.Picture pic : pictures) {
            piclist.add(pic.getImgSrc());
            titlelist.add(pic.getTitle());
        }
        NewsPhotoPagerAdapter adapter = new NewsPhotoPagerAdapter(piclist, this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv.setText(titlelist.get(position));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
