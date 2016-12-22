package com.example.liubiao.mvptest.di.module;

import android.app.Activity;

import com.example.liubiao.mvptest.di.scope.PerActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liubiao on 2016/12/2.
 */
@Module
public class ActivityModule {
    private final Activity activity;
    public ActivityModule(Activity activity)
    {
        this.activity=activity;
    }
    @Provides@PerActivity
    public Activity provideActivity()
    {
        return activity;
    }


}
