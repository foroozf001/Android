package com.example.a10_numberstrivia;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private NumbersRepository numbersRepository = new NumbersRepository();
    private MutableLiveData<String> trivia = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<String> getTrivia() {
        return trivia;
    }
    public void getRandomNumberTrivia() {
        numbersRepository
            .getRandomNumberTrivia()
            .enqueue(new Callback<Trivia>() {
                @Override
                public void onResponse(@NonNull Call<Trivia> call, @NonNull Response<Trivia> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        trivia.setValue(response.body().getText());
                    } else {
                        error.setValue("Api Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Trivia> call, @NonNull Throwable t) {
                    error.setValue("Api Error: " + t.getMessage());
                }
            });
    }
}