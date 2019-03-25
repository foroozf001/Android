package com.example.a09_gamebacklog_mv;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private GameRepository gameRepository;
    private LiveData<List<Game>> games;

    public MainViewModel(@NonNull Application application) {
        super(application);

        gameRepository = new GameRepository(application.getApplicationContext());
        games = gameRepository.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return games;
    }

    public void insert(Game item) {
        gameRepository.insert(item);
    }

    public void insert(List<Game> items) {
        gameRepository.insert(items);
    }

    public void delete(Game item) {
        gameRepository.delete(item);
    }

    public void delete(List<Game> items) {
        gameRepository.delete(items);
    }

    public void update(Game item) {
        gameRepository.update(item);
    }
}
