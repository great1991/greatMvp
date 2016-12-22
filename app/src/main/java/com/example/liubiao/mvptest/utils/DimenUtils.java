package com.example.liubiao.mvptest.utils;

import android.content.Context;
import android.view.WindowManager;

import com.example.liubiao.mvptest.App;

/**
 * Created by liubiao on 2016/12/9.
 */

public class DimenUtils {
    public static float dp2dx(int dp)
    {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
    public static int getScreenWidth(Context context)
    {
        WindowManager service = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return service.getDefaultDisplay().getWidth();
    }
}

