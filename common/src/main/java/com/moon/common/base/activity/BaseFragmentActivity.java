package com.moon.common.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.moon.common.R;
import com.moon.common.base.mvp.BasePresenter;

/**
 * 本Activity仅提供一个Fragment的容器
 *
 * @param <P>
 */
public abstract class BaseFragmentActivity<P extends BasePresenter> extends BaseActivity<P> {

    @Override
    public int getLayoutRes() {
        return R.layout.common_activity_fragment;
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, createFragment()).commit();
    }

    /**
     * 返回一个Fragment实例
     *
     * @return
     */
    protected abstract Fragment createFragment();
}
