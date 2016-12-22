package com.example.liubiao.mvptest.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptext.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liubiao on 2016/12/22.
 */
public class BeautyDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    public void initInject() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_beauty_details;
    }

    @Override
    protected void initView() {
        String url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail).into(photoView);
        initEvent();

    }

    private void initEvent() {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {

            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
    }


}
