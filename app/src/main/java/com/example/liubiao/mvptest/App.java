package com.example.liubiao.mvptest;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.liubiao.mvptest.common.Constant;

import com.example.liubiao.mvptest.di.component.AppComponent;
import com.example.liubiao.mvptest.di.component.DaggerAppComponent;
import com.example.liubiao.mvptest.di.module.AppModule;
import com.example.liubiao.mvptest.utils.MyUtils;

import me.mvp.greendao.DaoMaster;
import me.mvp.greendao.DaoSession;
import me.mvp.greendao.NewsChannelTableDao;

/**
 * Created by liubiao on 2016/12/2.
 */

public class App extends Application {
    private static Context mApp;
    private static AppComponent appComponent;
    private static DaoSession daoSession;



    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        //初始化
        initAppComponent();
        initDataBase();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constant.DB_Name, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static Context getAppContext() {
        return mApp;
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }
    public  static NewsChannelTableDao getTableDao() {
        return daoSession.getNewsChannelTableDao();
    }
    public static boolean isHavePhoto() {
        return MyUtils.getSharePreference().getBoolean(Constant.SHOW_NEWS_PHOTO, true);
    }




}
