package com.moon.common.base.net.response;

import com.moon.common.base.entity.Entity;

/**
 * 网络请求返回基类
 *
 * @param <T>
 */
public class BaseResponse<T> implements Entity {

    private String errorCode;
    private String errorMsg;
    private T data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String code) {
        this.errorCode = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
