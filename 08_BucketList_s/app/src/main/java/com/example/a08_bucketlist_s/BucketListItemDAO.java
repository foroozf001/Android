package com.example.a08_bucketlist_s;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketListItemDAO {
    @Insert
    void insert(BucketListItem item);

    @Delete
    void delete(BucketListItem item);

    @Delete
    void delete(List<BucketListItem> items);

    @Update
    void updateBucketListItem(BucketListItem item);

    @Query("SELECT * from bucketlist_table")
    List<BucketListItem> getAllBucketListItems();
}
