package com.moon.common.base.net.response.observer;


import com.moon.common.base.net.request.NetConstant;
import com.moon.common.base.net.response.BaseResponse;
import com.moon.library.utils.StringUtils;
import com.moon.library.utils.ToastUtils;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class BaseResponseObserver<T> implements SingleObserver<BaseResponse<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(BaseResponse<T> tBaseResponse) {

        String resultCode = tBaseResponse.getErrorCode();
        if (NetConstant.Codes.SUCCESS.equals(resultCode)) {
            onBusinessSuccess(tBaseResponse.getData());
        } else {
            onBusinessFail(tBaseResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ToastUtils.show(e.getMessage());
    }

    public void onBusinessFail(BaseResponse<T> tBaseResponse) {
        String resultMsg = tBaseResponse.getErrorMsg();
        if (StringUtils.isEmpty(resultMsg)) {
            resultMsg = "服务端异常，请稍后再试~";
        }

        ToastUtils.show(resultMsg);
    }

    /**
     * 业务请求数据成功
     *
     * @param data
     */
    public abstract void onBusinessSuccess(T data);
}
