package com.moon.mvp.demo.main.request;

import com.moon.common.base.net.response.BaseResponse;
import com.moon.mvp.entity.HotWord;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MainApiService {

    @GET("hotkey/json")
    Single<BaseResponse<List<HotWord>>> test();

}
