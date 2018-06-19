package com.moon.mvp;


public interface IView extends ILifecycle {

    void showLoading();

    void hideLoading();

    void showEmptyLoading();

    void hideEmptyLoading();

    void showMessage(String message);

}
