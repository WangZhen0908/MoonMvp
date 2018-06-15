package com.moon.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;


public interface IView {

    void showLoading();

    void hideLoading();

    void showEmptyLoading();

    void hideEmptyLoading();

    void showMessage(String message);

    <T> LifecycleTransformer<T> bindLifecycle();

}
