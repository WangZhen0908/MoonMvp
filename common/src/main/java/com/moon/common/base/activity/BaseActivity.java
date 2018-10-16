package com.moon.common.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.WindowManager;

import com.moon.common.base.mvp.BasePresenter;
import com.moon.library.utils.ToastUtils;
import com.moon.mvp.MVPActivity;
import com.moon.widget.LoadingDialog;

public abstract class BaseActivity<P extends BasePresenter> extends MVPActivity<P> {

    private LoadingDialog mLoadingDialog;

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
        ToastUtils.show(message);
    }

    @Override
    public void showLoading() {

        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }

    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
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
