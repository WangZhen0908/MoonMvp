package com.moon.mvp.demo.main.presenter;

import com.moon.mvp.demo.main.contract.MainContract;

public class MainPresenter extends MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }
}
