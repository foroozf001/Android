package com.example.a10_numberstrivia;

import retrofit2.Call;

public class NumbersRepository {
    private NumbersApiService numbersApiService = NumbersApi.create();

    public Call<Trivia> getRandomNumberTrivia() {
        return numbersApiService.getRandomNumberTrivia();
    }

}