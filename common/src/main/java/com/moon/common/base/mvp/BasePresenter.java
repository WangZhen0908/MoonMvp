package com.moon.common.base.mvp;

import com.moon.mvp.IPresenter;

public abstract class BasePresenter<V extends BaseView> implements IPresenter {

    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

}
