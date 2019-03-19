package com.example.a07_shoppinglist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Delete
    void delete(List<Product> products);

    @Query("SELECT * from product_table")
    List<Product> getAllProducts();
}