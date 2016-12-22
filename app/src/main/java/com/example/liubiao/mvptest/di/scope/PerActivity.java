package com.example.liubiao.mvptest.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by liubiao on 2016/12/2.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
