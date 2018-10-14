package com.moon.retrofit;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final int MAX_TIME = 60;
    private static Dispatcher sDispatcher = new Dispatcher();
    private static ConnectionPool sConnectionPool = new ConnectionPool();

    public static <T> T create(final Class<T> service, String baseUrl, Interceptor... interceptors) {
        return new Retrofit.Builder()
                .client(getOkHttpClient(interceptors))
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(service);
    }

    private static OkHttpClient getOkHttpClient(Interceptor... interceptors) {
        //HTTPS 验证
        X509TrustManager x509TrustManager = HttpsHelper.unSafeTrustManager();
        SSLSocketFactory sslSocketFactory = HttpsHelper.getSSLSocketFactory(x509TrustManager);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptors.length > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        return builder.connectTimeout(MAX_TIME, TimeUnit.SECONDS)
                .readTimeout(MAX_TIME, TimeUnit.SECONDS)
                .writeTimeout(MAX_TIME, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .dispatcher(sDispatcher)
                .connectionPool(sConnectionPool)
                .sslSocketFactory(sslSocketFactory, x509TrustManager)
                .hostnameVerifier(HttpsHelper.getHostnameVerifierUnSafe())
                .build();
    }

}
