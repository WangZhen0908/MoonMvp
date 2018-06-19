package com.moon.common.base.net.response.observer;

import com.moon.common.base.net.request.NetConstant;
import com.moon.common.base.net.response.BaseResponse;
import com.moon.library.utils.StringUtils;
import com.moon.library.utils.ToastUtils;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class BaseNoDataResponseObserver implements SingleObserver<BaseResponse> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(BaseResponse tBaseResponse) {
        String resultCode = tBaseResponse.getErrorCode();
        if (String.valueOf(NetConstant.Codes.SUCCESS).equals(resultCode)) {
            onBusinessSuccess(tBaseResponse);
        } else {
            onBusinessFail(tBaseResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ToastUtils.show(e.getMessage());
    }

    public void onBusinessFail(BaseResponse tBaseResponse) {
        String resultMsg = tBaseResponse.getErrorMsg();

        if (StringUtils.isEmpty(resultMsg)) {
            resultMsg = "服务端异常，请稍后再试~";
        }

        if (StringUtils.isNotEmpty(resultMsg)) {
            ToastUtils.show(resultMsg);
        }
    }

    public abstract void onBusinessSuccess(BaseResponse tBaseResponse);
}
