package com.example.liubiao.mvptest.di.component;

import android.content.Context;

import com.example.liubiao.mvptest.di.module.AppModule;
import com.example.liubiao.mvptest.di.scope.ContextLife;
import com.example.liubiao.mvptest.di.scope.PerApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by liubiao on 2016/12/2.
 */
@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {
    @ContextLife("Application")
    Context getApplicationContext();

}
