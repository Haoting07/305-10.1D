package com.example.a61d.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
public class ApiClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://172.16.11.111:5000/"; // 本地 Flask API

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)   // 设置连接超时为 60 秒
                    .readTimeout(90, TimeUnit.SECONDS)      // 设置读取超时为 90 秒
                    .writeTimeout(90, TimeUnit.SECONDS)     // 设置写入超时为 90 秒
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
