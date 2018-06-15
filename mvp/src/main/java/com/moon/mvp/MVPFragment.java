package com.moon.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moon.mvp.impl.ISupportView;
import com.moon.mvp.impl.LazySupportView;
import com.trello.rxlifecycle2.components.support.RxFragment;

public abstract class MVPFragment<P extends IPresenter> extends RxFragment implements IView, Init<P>, ISupportView {


    protected Activity mActivity;
    protected P mPresenter;
    protected Bundle args;
    private LazySupportView mLazySupprotView = new LazySupportView(this);

    @Override
    public int getMenuRes() {
        return INVALID_MENU;
    }

    @Override
    public abstract P createPresenter();

    @Override
    public void initViews(View view, @Nullable Bundle args) {

    }

    @Override
    public void initMenus(Menu menu) {

    }

    @Override
    public void initData(@Nullable Bundle args) {

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
        initData(args);
        setHasOptionsMenu(getMenuRes() != INVALID_MENU);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(mActivity).inflate(getLayoutRes(), container, false);
        mLazySupprotView.onCreateView();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (savedInstanceState != null) {
            args = savedInstanceState;
        }
        this.args = args;
        mPresenter = createPresenter();
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mLazySupprotView.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onLazyInitView() {

    }

    @Override
    public void onFragmentShow() {

    }
}
