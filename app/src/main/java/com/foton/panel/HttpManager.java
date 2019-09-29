package com.foton.panel;

import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.foton.library.net.converter.GsonConverterFactory;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络请求工具类
 *
 * @author wilson
 * @since 2019年9月17日10:57:15
 */
public class HttpManager {
    private final String TAG = "HttpManager";
    private static volatile HttpManager instance;
    private Retrofit retrofit;
    private ApiService apiService;

    private final int CONNECT_TIME = 30;
    private final int WRITE_TIME = 30;
    private final int READ_TIME = 30;

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    private HttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        /*The log of http will be print when the debug model*/
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i(TAG, message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        apiService = retrofit.create(ApiService.class);
    }


    /**
     * if u need to use this method,please add permission in the manifests
     *
     * @return ApiService Object
     */
    @RequiresPermission("android.permission.INTERNET")
    public ApiService getApiService() {
        return apiService;
    }
}
