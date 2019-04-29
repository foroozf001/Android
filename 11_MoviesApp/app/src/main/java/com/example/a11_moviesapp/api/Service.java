package com.example.a11_moviesapp.api;

import com.example.a11_moviesapp.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("discover/movie")
    Call<MoviesResponse> getPopularMovies(
            @Query("year") Integer year,
            @Query("api_key") String apiKey
    );

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("year") Integer year,
            @Query("api_key") String apiKey
    );
}
