package com.example.liubiao.mvptest.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.liubiao.mvptest.di.scope.ContextLife;
import com.example.liubiao.mvptest.di.scope.PerFragment;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptest.mvp.ui.fragment.base.BaseFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liubiao on 2016/12/2.
 */
@Module
public class FragmentModule {
    private final Fragment fragment;
    public FragmentModule(Fragment fragment)
    {
        this.fragment=fragment;
    }
    @Provides
    @PerFragment
    public Activity provideActivity()
    {
        return  fragment.getActivity();
    }
    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext()
    {
        return fragment.getContext();
    }
}
