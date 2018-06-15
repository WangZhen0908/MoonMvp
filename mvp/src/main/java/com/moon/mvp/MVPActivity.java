package com.moon.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

public abstract class MVPActivity<P extends IPresenter> extends RxFragmentActivity implements IView, Init<P> {

    protected P mPresenter;

    @Override
    public int getMenuRes() {
        return INVALID_MENU;
    }

    @Override
    public abstract P createPresenter();

    @Override
    public void initViews(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initMenus(Menu menu) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutRes = getLayoutRes();
        if (INVALID_LAYOUT != layoutRes) {
            setContentView(getLayoutRes());
        }
        initData(savedInstanceState);
        mPresenter = createPresenter();
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
}
