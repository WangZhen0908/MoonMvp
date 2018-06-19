package com.moon.library.utils;

import android.app.Application;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

public class AppUtils {

    private static Application sApplication;
    private static Resources sResources;

    public static void init(Application application) {
        sApplication = application;
    }

    public static Application getApplication() {
        return sApplication;
    }


    public static int getColor(@ColorRes int resId) {
        return sApplication.getResources().getColor(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs) {
        return sApplication.getResources().getString(resId, formatArgs);
    }
}
