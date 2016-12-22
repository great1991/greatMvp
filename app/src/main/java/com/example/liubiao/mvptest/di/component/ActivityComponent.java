package com.example.liubiao.mvptest.di.component;

import android.app.Activity;

import com.example.liubiao.mvptest.di.module.ActivityModule;
import com.example.liubiao.mvptest.di.scope.PerActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.BeautyActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsAddActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsListDetailsActivity;
import dagger.Component;

/**
 * Created by liubiao on 2016/12/2.
 */
@PerActivity
@Component(dependencies = AppComponent.class ,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(NewsActivity newsActivity);
    void inject(NewsAddActivity newsActivity);
    void inject(NewsListDetailsActivity newsActivity);
    void inject(BeautyActivity newsActivity);
    Activity getActivity();
}
