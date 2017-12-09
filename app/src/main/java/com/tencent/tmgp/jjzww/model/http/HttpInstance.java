package com.tencent.tmgp.jjzww.model.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhouh on 2017/8/15.
 */
public class HttpInstance {
    private static HttpInstance instance;
    private OkHttpClient client;

    public static synchronized HttpInstance getInstance() {
        if (instance == null) {
            instance = new HttpInstance();
        }
        return instance;
    }

    private HttpInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("http==",message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public OkHttpClient getClient() {
        if (client != null) {
            return client;
        }
        return null;
    }

}
