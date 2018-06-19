package com.moon.common.base.fragment;

import android.support.annotation.IdRes;
import android.view.View;

import com.moon.common.base.mvp.BasePresenter;
import com.moon.library.utils.ToastUtils;
import com.moon.mvp.MVPFragment;

public abstract class BaseFragment<P extends BasePresenter> extends MVPFragment<P> {

    protected <T extends View> T $BindView(View contentView, @IdRes int resId) {
        return contentView.findViewById(resId);
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.show(message);
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

}
