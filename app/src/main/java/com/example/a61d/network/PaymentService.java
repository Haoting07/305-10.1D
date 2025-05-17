package com.example.a61d.network;

import com.example.a61d.models.PaymentInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentService {
    // 请将此链接替换为你自己的 mocky.io 链接
    @GET("v3/c463859d-fc2b-4fa0-b327-873232dc7264")
    Call<PaymentInfo> getPaymentInfo();
}
