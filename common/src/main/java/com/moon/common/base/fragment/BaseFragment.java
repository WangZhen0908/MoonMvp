package com.moon.common.base.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moon.common.base.mvp.BasePresenter;
import com.moon.library.utils.ToastUtils;
import com.moon.mvp.MVPFragment;

public abstract class BaseFragment<P extends BasePresenter> extends MVPFragment<P> implements ISupportView {

    private LazySupportView mLazySupportView = new LazySupportView(this);

    protected <T extends View> T $BindView(View contentView, @IdRes int resId) {
        return contentView.findViewById(resId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mLazySupportView.onCreateView();
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mLazySupportView.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onLazyInitView() {

    }

    @Override
    public void onFragmentShow() {

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
