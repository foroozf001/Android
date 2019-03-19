package com.example.a08_bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BucketListItem.class}, version = 1, exportSchema = false)

public abstract class BucketListRoomDatabase extends RoomDatabase{
    private final static String NAME_DATABASE = "bucketlist_database";
    public abstract BucketListItemDAO bucketListItemDao();
    private static volatile BucketListRoomDatabase INSTANCE;

    public static BucketListRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (BucketListRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BucketListRoomDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
