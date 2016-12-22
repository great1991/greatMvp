package com.example.liubiao.mvptest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.WindowManager;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.common.Constant;

import rx.Subscription;

/**
 * Created by liubiao on 2016/12/6.
 */

public class MyUtils {

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharePreference()
    {
        sharedPreferences = App.getAppContext().getSharedPreferences(Constant.SHARES_COLOURFUL_NEWS, Context.MODE_PRIVATE);

        return sharedPreferences;
    }


    public static void setTabsMode(Context context,TabLayout tabs) {
        //获取屏幕的宽度与tab 的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int tabWidth=0;
        for (int i=0;i<tabs.getChildCount();i++)
        {
            View view = tabs.getChildAt(i);
            view.measure(0,0);
            int measuredWidth = view.getMeasuredWidth();
            tabWidth+=measuredWidth;
        }
        if(screenWidth>=tabWidth)
        {
            tabs.setTabMode(TabLayout.MODE_FIXED);
        }else
        {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    public static String formatTime(String time) {
        String s = time.substring(5, 16);
        return s;
    }

    public static void dismissSubScript(Subscription mSubscription) {
        if(mSubscription!=null&&mSubscription.isUnsubscribed())
        {
            mSubscription.unsubscribe();
        }
    }
}
