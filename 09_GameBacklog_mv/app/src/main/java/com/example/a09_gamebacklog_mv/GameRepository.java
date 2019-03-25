package com.example.a09_gamebacklog_mv;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GameRepository {
    private GameRoomDatabase db;
    private GameDao gameDao;
    private LiveData<List<Game>> games;
    private Executor executor = Executors.newSingleThreadExecutor();

    public GameRepository(Context context) {
        db = GameRoomDatabase.getInstance(context);
        gameDao = db.gameDao();
        games = gameDao.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return games;
    }

    public void insert(final Game item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().insertGame(item);
            }
        });
    }

    public void insert(final List<Game> items) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().insertGames(items);
            }
        });
    }

    public void delete(final Game item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().deleteGame(item);
            }
        });
    }

    public void delete(final List<Game> items) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().deleteGames(items);
            }
        });
    }

    public void update(final Game item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().updateGame(item);
            }
        });
    }
}
