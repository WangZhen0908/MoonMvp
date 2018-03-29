package com.moon.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public interface IView {

    void showLoading();

    void hideLoading();

    void showEmptyLoading();

    void hideEmptyLoading();

    void showMessage(String message);

    <T> LifecycleTransformer<T> bindLifecycle();

}
