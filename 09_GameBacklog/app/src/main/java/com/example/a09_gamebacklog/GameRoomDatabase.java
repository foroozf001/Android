package com.example.a09_gamebacklog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Game.class}, version = 1, exportSchema = false)

public abstract class GameRoomDatabase extends RoomDatabase{
    private final static String NAME_DATABASE = "game_backlog_db";
    public abstract GameDAO gameDao();
    private static volatile GameRoomDatabase INSTANCE;

    public static GameRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (GameRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameRoomDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
