package com.example.a09_gamebacklog_mv;

import android.arch.persistence.room.TypeConverter;

public class GameStatusConverter {
    @TypeConverter
    public static GameStatus toBacklogStatus(int statusInt) {
        return GameStatus.values()[statusInt];
    }

    @TypeConverter
    public static int fromBacklogStatus(GameStatus status) {
        return status.getValue();
    }
}
