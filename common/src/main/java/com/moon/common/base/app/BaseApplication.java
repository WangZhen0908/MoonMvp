package com.moon.common.base.app;

import android.support.multidex.MultiDexApplication;

public abstract class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInitHelper.init(this);
    }
}
