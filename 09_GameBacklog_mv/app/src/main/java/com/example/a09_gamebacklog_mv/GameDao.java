package com.example.a09_gamebacklog_mv;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM backlog_items") LiveData<List<Game>> getAllGames();

    @Delete void deleteGame(Game item);
    @Delete void deleteGames(List<Game> items);
    @Insert void insertGame(Game item);
    @Insert void insertGames(List<Game> items);
    @Update void updateGame(Game item);
}
