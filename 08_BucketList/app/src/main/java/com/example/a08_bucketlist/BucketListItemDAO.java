package com.example.a08_bucketlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BucketListItemDAO {
    @Insert
    void insert(BucketListItem item);

    @Delete
    void delete(BucketListItem item);

    @Delete
    void delete(List<BucketListItem> items);

    @Query("SELECT * from bucketlist_table")
    List<BucketListItem> getAllProducts();
}
