package com.moon.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

public abstract class MVPActivity<P extends IPresenter> extends RxFragmentActivity implements IView, Init<P> {

    protected P mPresenter;

    @Override
    public @MenuRes
    int getMenuRes() {
        return INVALID_MENU;
    }

    @Override
    public void initViews(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initMenus(Menu menu) {

    }

    @Override
    public void initExtraData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void requestData() {

    }

    /**
     * 调用顺序：<br>
     * 1)getLayoutRes<br>
     * 2)initExtraData<br>
     * 3)createPresenter<br>
     * 4)initViews<br>
     * 5)requestData<br>
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutRes = getLayoutRes();
        if (INVALID_LAYOUT != layoutRes) {
            setContentView(getLayoutRes());
        }
        initExtraData(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        initViews(null, savedInstanceState);

        if (getMenuRes() == INVALID_MENU) {
            requestData();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 请使用initMenus(Menu menu)
     */
    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        int menuRes = getMenuRes();
        if (menuRes != INVALID_MENU) {
            getMenuInflater().inflate(menuRes, menu);
            initMenus(menu);
            requestData();
        }
        return true;
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return bindToLifecycle();
    }
}
