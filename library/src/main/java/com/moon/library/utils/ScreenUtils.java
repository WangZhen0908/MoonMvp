package com.moon.library.utils;

import android.content.Context;

/**
 * Created by wangzhen1 on 2018/3/14.
 */

public class ScreenUtils {


    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        if (statusBarHeight > 0) {
            return statusBarHeight;
        }


        /**
         * 获取状态栏高度——方法2
         * */
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (statusBarHeight > 0) {
            return statusBarHeight;
        }

        return -1;

    }

}
