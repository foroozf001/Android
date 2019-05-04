package com.example.a11_moviesapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.a11_moviesapp.model.Movie;
import com.example.a11_moviesapp.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MoviesRepository moviesRepository = new MoviesRepository();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return movies;
    }

    public void getPopularMovies(int year, String apiKey) {
        moviesRepository
                .getPopularMovies(year, apiKey)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            movies.setValue(response.body().getResults());
                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        error.setValue("API error: " + t.getMessage());
                    }
                });
    }
}
