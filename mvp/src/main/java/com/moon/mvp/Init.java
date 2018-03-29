package com.moon.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

/**
 * Created by wangzhen1 on 2017/11/15.
 */

public interface Init<P extends IPresenter> {
    /**
     * 没有布局
     */
    int INVALID_LAYOUT = -1;
    /**
     * 没有menu布局
     */
    int INVALID_MENU = -2;

    int getLayoutRes();

    int getMenuRes();

    void initViews(View view, @Nullable Bundle savedInstanceState);

    void initMenus(Menu menu);

    /**
     * 在这里初始化Intent传递的参数,以及组件恢复时的参数
     *
     * @param savedInstanceState onCreate(Bundle savedInstanceState)
     */
    void initData(@Nullable Bundle savedInstanceState);

    P createPresenter();

    void requestData();

}
