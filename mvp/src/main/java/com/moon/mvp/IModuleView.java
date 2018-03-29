package com.moon.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 把View分成头，内容区，搜索，错误，空布局
 */

public interface IModuleView {
    /**
     * 没有布局
     */
    int INVALID_LAYOUT = -1;

    public View initModule(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 头布局
     *
     * @return
     */
    @LayoutRes
    public int getHeadLayoutId();

    /**
     * 网络请求错误
     *
     * @return
     */
    @LayoutRes
    public int getNetErrorLayoutId();

    /**
     * 内容区
     *
     * @return
     */
    @LayoutRes
    public int getContentLayoutId();

    /**
     * 为空
     *
     * @return
     */
    public int getEmptyLayoutId();

    /**
     * 无权限
     *
     * @return
     */
    public int getNotLimitId();

    /**
     * 显示内容
     */
    public void showContextView();

    /**
     * 显示为空
     */
    public void showEmptyView();

    /**
     * 显示网络请求失败
     */
    public void showNetFailView();

    public void showNotLimitView();
}
