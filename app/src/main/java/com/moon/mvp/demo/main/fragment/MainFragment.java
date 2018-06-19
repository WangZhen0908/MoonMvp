package com.moon.mvp.demo.main.fragment;

import android.os.Bundle;

import com.moon.common.base.fragment.BaseFragment;
import com.moon.mvp.demo.R;
import com.moon.mvp.demo.main.contract.MainContract;
import com.moon.mvp.demo.main.presenter.MainPresenter;

public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_fragment_main;
    }
}
