package com.moon.common.base.net.request.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class CommonHeaderInterceptor implements Interceptor {

    private Context mContext;

    public CommonHeaderInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        String token = "";
        if (token != null) {
            builder.addHeader("token", token);
        }

        Request request = builder.build();
//        Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
//        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}