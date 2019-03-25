package com.example.a09_gamebacklog_mv;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameRoomDatabase extends RoomDatabase {
    private final static String DATABASE_NAME = "gamebacklog_db";
    public abstract GameDao gameDao();

    private static volatile GameRoomDatabase INSTANCE;

    public static GameRoomDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (GameRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GameRoomDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
