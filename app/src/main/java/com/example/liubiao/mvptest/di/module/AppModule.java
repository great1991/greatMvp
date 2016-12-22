package com.example.liubiao.mvptest.di.module;

import android.content.Context;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.di.scope.ContextLife;
import com.example.liubiao.mvptest.di.scope.PerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liubiao on 2016/12/2.
 */
@Module
public class AppModule {

    private final App application;

    public AppModule(App application)
    {
        this.application =application;
    }
    @Provides
    @PerApp
    @ContextLife("Application")
    Context provideApplicationContext()
    {
        return application.getApplicationContext();
    }

}
