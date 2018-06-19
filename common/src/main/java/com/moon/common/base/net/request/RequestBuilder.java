package com.moon.common.base.net.request;

import java.util.List;

import okhttp3.Interceptor;

public interface RequestBuilder {
    public String getBaseUrl();

    /**
     * 获取拦截器
     *
     * @return
     */
    public List<Interceptor> getInterceptors();
}
