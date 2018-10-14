package com.moon.common.base.net.request;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moon.retrofit.RetrofitHelper;
import com.moon.common.base.net.request.interceptor.CommonHeaderInterceptor;
import com.moon.common.base.net.request.interceptor.CommonResponseInterceptor;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

public abstract class BaseApiRequestBuilder<T> implements RequestBuilder {

    protected T mServer = null;
    protected Context mContext;


    public BaseApiRequestBuilder(@NonNull Context context) {
        mContext = context;
        List<Interceptor> interceptors = getInterceptors();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (interceptors != null && !interceptors.isEmpty()) {
            mServer = RetrofitHelper.create(entityClass, getBaseUrl(), interceptors.toArray(new Interceptor[]{}));
        } else {
            mServer = RetrofitHelper.create(entityClass, getBaseUrl());
        }

    }

    @Override
    public String getBaseUrl() {
        return NetConstant.getBaseUrl();
    }

    @Override
    public List<Interceptor> getInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new CommonHeaderInterceptor(mContext));
        interceptors.add(new CommonResponseInterceptor(mContext));
        return interceptors;
    }

}