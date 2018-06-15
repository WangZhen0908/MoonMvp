package com.moon.common.base.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.moon.common.base.mvp.BasePresenter;
import com.moon.mvp.MVPActivity;

public abstract class BaseActivity<P extends BasePresenter> extends MVPActivity<P> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSecure()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
    }


    /**
     * @return true不允许该界面截屏, false允许在该界面截屏
     */
    protected boolean isSecure() {
        return false;
    }

}
