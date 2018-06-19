package com.moon.mvp;


public interface IView<P extends IPresenter> extends ILifecycle {

    P createPresenter();

    void showLoading();

    void hideLoading();

    void showEmptyLoading();

    void hideEmptyLoading();

    void showMessage(String message);

}
