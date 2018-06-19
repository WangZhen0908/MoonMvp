package com.moon.mvp.demo.main.request;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moon.common.base.net.request.BaseApiRequestBuilder;
import com.moon.common.base.net.response.BaseResponse;
import com.moon.mvp.entity.HotWord;

import java.util.List;

import io.reactivex.Single;

public class MainRequestBuilder extends BaseApiRequestBuilder<MainApiService> {

    public MainRequestBuilder(@NonNull Context context) {
        super(context);
    }

    @Override
    public String getBaseUrl() {
        return "http://www.wanandroid.com/";
    }

    public Single<BaseResponse<List<HotWord>>> test() {
        return mServer.test();
    }
}
