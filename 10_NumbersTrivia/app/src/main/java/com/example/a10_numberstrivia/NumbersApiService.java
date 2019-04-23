package com.example.a10_numberstrivia;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NumbersApiService {
    @GET("/random/trivia?json")
    Call<Trivia> getRandomNumberTrivia();
}