package com.moon.common.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.WindowManager;

import com.moon.common.base.mvp.BasePresenter;
import com.moon.mvp.MVPActivity;

public abstract class BaseActivity<P extends BasePresenter> extends MVPActivity<P> {

    @Override
    public abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSecure()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    protected <T extends View> T $BindView(@IdRes int resId) {
        return findViewById(resId);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showEmptyLoading() {

    }

    @Override
    public void hideEmptyLoading() {

    }

    /**
     * @return true不允许该界面截屏, false允许在该界面截屏
     */
    protected boolean isSecure() {
        return false;
    }

}
