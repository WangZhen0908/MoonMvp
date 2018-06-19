package com.moon.common.base.net.request.interceptor;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CommonResponseInterceptor implements Interceptor {
    private Context mContext;

    public CommonResponseInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String requestUrl = request.url().toString();

        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();


        String result = response.body().string();
        Log.i("info", "请求地址：" + requestUrl);
        Log.i("info", "请求结果：" + result);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            String resultCode = jsonObject.optString("code");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Response.Builder resBuilder = response.newBuilder();
        resBuilder.body(ResponseBody.create(mediaType, result));

        return resBuilder.build();
    }

}
