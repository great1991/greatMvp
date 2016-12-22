package com.example.liubiao.mvptest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.liubiao.mvptest.App;

/**
 * Created by liubiao on 2016/12/8.
 */

public class NetUtils {
    public static boolean isNetworkReachable()
    {
        ConnectivityManager cm = (ConnectivityManager) App.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
