package com.moon.mvp;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

/**
 * Created by wangzhen1 on 2017/11/15.
 */

public interface Init<P extends IPresenter> {
    int INVALID_LAYOUT = 0;
    int INVALID_MENU = 0;

    /**
     * 返回布局资源ID
     *
     * @return
     */
    int getLayoutRes();

    /**
     * 返回menu菜单id
     *
     * @return
     */
    @MenuRes
    int getMenuRes();

    P createPresenter();

    /**
     * 初始化View
     *
     * @param view
     * @param savedInstanceState
     */
    void initViews(View view, @Nullable Bundle savedInstanceState);

    /**
     * 初始化menu菜单
     *
     * @param menu
     */
    void initMenus(Menu menu);

    /**
     * 初始化组件间传递的数据
     *
     * @param savedInstanceState
     */
    void initExtraData(@Nullable Bundle savedInstanceState);

    /**
     * 第一次请求数据
     */
    void requestData();

}
