package com.example.a11_moviesapp.viewmodel;

import com.example.a11_moviesapp.api.Client;
import com.example.a11_moviesapp.api.Service;
import com.example.a11_moviesapp.model.MoviesResponse;

import retrofit2.Call;

public class MoviesRepository {
    Service apiService = Client.create();

    public Call<MoviesResponse> getPopularMovies(int year, String apiKey) {
        return apiService.getPopularMovies(year, apiKey);
    }
}
