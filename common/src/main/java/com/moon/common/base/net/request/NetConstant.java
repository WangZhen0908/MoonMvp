package com.moon.common.base.net.request;

public final class NetConstant {

    public interface Codes {
        String SUCCESS = "0";
    }

    private static String BASE_URL = "";

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
