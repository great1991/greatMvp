package com.example.liubiao.mvptest.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.liubiao.mvptest.di.module.FragmentModule;
import com.example.liubiao.mvptest.di.scope.ContextLife;
import com.example.liubiao.mvptest.di.scope.PerFragment;
import com.example.liubiao.mvptest.mvp.ui.fragment.NewsListFragment;
import com.example.liubiao.mvptest.mvp.ui.fragment.base.BaseFragment;

import dagger.Component;

/**
 * Created by liubiao on 2016/12/2.
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Application")
    Context getApplicationContext();
    @ContextLife("Activity")
    Context getActivityContext();
    Activity getActivity();
    void inject(NewsListFragment fragment);
}
