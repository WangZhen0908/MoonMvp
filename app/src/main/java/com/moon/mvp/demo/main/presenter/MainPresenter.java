package com.moon.mvp.demo.main.presenter;

import com.moon.common.base.net.response.BaseResponse;
import com.moon.common.base.net.response.observer.BaseResponseObserver;
import com.moon.common.base.net.rx.NetWorkUtils;
import com.moon.common.base.net.rx.schedulers.SchedulerProvider;
import com.moon.library.utils.ACache;
import com.moon.library.utils.AppUtils;
import com.moon.library.utils.CollectionUtils;
import com.moon.library.utils.SessionHelper;
import com.moon.mvp.demo.main.contract.MainContract;
import com.moon.mvp.demo.main.request.MainRequestBuilder;
import com.moon.mvp.demo.main.sp.MainPreference;
import com.moon.mvp.entity.HotWord;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends MainContract.Presenter {

    private MainRequestBuilder mMainRequestBuilder;

    public MainPresenter(MainContract.View view) {
        super(view);
        mMainRequestBuilder = new MainRequestBuilder(AppUtils.getApplication());
    }

    @Override
    public void test() {

        List<HotWord> words = ACache.get(AppUtils.getApplication()).getAsObject("words");

        if (CollectionUtils.isValid(words)) {
            mView.showMessage("缓存热词数量：" + words.size());
            mView.showHotWordsCount(words.size());

            return;
        }

        mMainRequestBuilder.test()
                .compose(NetWorkUtils.<BaseResponse<List<HotWord>>>netWorkLoadingScheduler(mView, SchedulerProvider.getInstance()))
                .compose(mView.<BaseResponse<List<HotWord>>>bindLifecycle())
                .subscribe(new BaseResponseObserver<List<HotWord>>() {

                    @Override
                    public void onBusinessSuccess(List<HotWord> data) {
                        SessionHelper
                                .getInstance(AppUtils.getApplication(), MainPreference.PREFERENCE_NAME)
                                .saveIntKey(MainPreference.TOTAL_WORDS_INT, data.size());

                        ACache.get(AppUtils.getApplication()).put("words", (ArrayList) data);
                        mView.showMessage("热词数量：" + data.size());
                        mView.showHotWordsCount(data.size());
                    }
                });
    }

    @Override
    public int getHotWords() {
        return SessionHelper
                .getInstance(AppUtils.getApplication(), MainPreference.PREFERENCE_NAME)
                .loadIntKey(MainPreference.TOTAL_WORDS_INT, 0);
    }
}
