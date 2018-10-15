package com.moon.mvp.demo.main.contract;

import com.moon.common.base.mvp.BasePresenter;
import com.moon.common.base.mvp.BaseView;

public interface MainContract {

    interface View extends BaseView {
        void showHotWordsCount(int count);
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void test();

        public abstract int getHotWords();
    }

}
