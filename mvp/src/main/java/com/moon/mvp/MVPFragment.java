package com.moon.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

public abstract class MVPFragment<P extends IPresenter> extends RxFragment implements IView, Init<P> {


    protected Activity mActivity;
    protected P mPresenter;

    @Override
    public @MenuRes
    int getMenuRes() {
        return INVALID_MENU;
    }

    @Override
    public void initViews(View view, @Nullable Bundle args) {

    }

    @Override
    public void initMenus(Menu menu) {

    }

    @Override
    public void initExtraData(@Nullable Bundle args) {

    }

    @Override
    public void requestData() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (savedInstanceState != null) {
            args = savedInstanceState;
        }
        initExtraData(args);
        setHasOptionsMenu(getMenuRes() != INVALID_MENU);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(mActivity).inflate(getLayoutRes(), container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (savedInstanceState != null) {
            args = savedInstanceState;
        }
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        initViews(view, args);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getMenuRes() == INVALID_MENU) {
            requestData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int menuRes = getMenuRes();
        if (menuRes != INVALID_MENU) {
            menu.clear();
            inflater.inflate(menuRes, menu);
            requestData();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
