package com.example.a61d.network;

import com.example.a61d.models.QuizResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("getQuiz")
    Call<QuizResponse> getQuiz(@Query("topic") String topic);
}
