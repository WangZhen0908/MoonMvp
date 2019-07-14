package com.moon.mvp.demo.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.moon.common.base.fragment.BaseFragment;
import com.moon.mvp.demo.R;
import com.moon.mvp.demo.main.contract.MainContract;
import com.moon.mvp.demo.main.presenter.MainPresenter;

public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {

    private TextView mHotWordsCountTv;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews(View view, @Nullable Bundle args) {
        super.initViews(view, args);
        mHotWordsCountTv = view.findViewById(R.id.hot_words_count_tv);
        mHotWordsCountTv.setText("上次：" + mPresenter.getHotWords());
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_fragment_main;
    }

    @Override
    public void requestData() {
        super.requestData();
        mPresenter.test();
    }

    @Override
    public void showHotWordsCount(int count) {
        mHotWordsCountTv.setText("最新：" + count);
    }
}
