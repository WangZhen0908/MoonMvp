package com.moon.common.base.app;

import android.app.Application;

import com.moon.library.utils.AppUtils;
import com.moon.library.utils.ToastUtils;

public class AppInitHelper {

    public static void init(Application application) {
        AppUtils.init(application);
        ToastUtils.init(application);
    }

}
