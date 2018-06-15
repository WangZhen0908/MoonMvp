package com.moon.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

/**
 * Created by wangzhen1 on 2017/11/15.
 */

public interface Init<P extends IPresenter> {
    int INVALID_LAYOUT = -1;
    int INVALID_MENU = -2;

    /**
     * 返回布局资源ID
     * @return
     */
    int getLayoutRes();

    int getMenuRes();

    void initViews(View view, @Nullable Bundle savedInstanceState);

    void initMenus(Menu menu);

    void initData(@Nullable Bundle savedInstanceState);

    P createPresenter();

    void requestData();

}
