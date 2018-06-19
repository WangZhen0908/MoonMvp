package com.moon.mvp.demo.main.presenter;

import com.moon.common.base.net.response.BaseResponse;
import com.moon.common.base.net.response.observer.BaseResponseObserver;
import com.moon.common.base.net.rx.NetWorkUtils;
import com.moon.library.utils.AppUtils;
import com.moon.mvp.demo.main.contract.MainContract;
import com.moon.mvp.demo.main.request.MainRequestBuilder;
import com.moon.mvp.entity.HotWord;

import java.util.List;

public class MainPresenter extends MainContract.Presenter {

    private MainRequestBuilder mMainRequestBuilder;

    public MainPresenter(MainContract.View view) {
        super(view);
        mMainRequestBuilder = new MainRequestBuilder(AppUtils.getApplication());
    }

    @Override
    public void test() {
        mMainRequestBuilder.test()
                .compose(NetWorkUtils.<BaseResponse<List<HotWord>>>singleNetWorkScheduler())
                .compose(mView.<BaseResponse<List<HotWord>>>bindLifecycle())
                .subscribe(new BaseResponseObserver<List<HotWord>>() {

                    @Override
                    public void onBusinessSuccess(List<HotWord> data) {
                        mView.showMessage("热词数量：" + data.size());
                    }
                });
    }
}
