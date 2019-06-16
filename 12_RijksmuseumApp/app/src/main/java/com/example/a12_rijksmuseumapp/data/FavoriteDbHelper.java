package com.example.a12_rijksmuseumapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.a12_rijksmuseumapp.model.Art;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.i(LOGTAG, "Database opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database close");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteContract.FavoriteEntry.COLUMN_ARTID + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_HEADERIMG + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_WEBIMG + " TEXT NOT NULL " +
                "); ";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addFavorite(Art object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_ARTID, object.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, object.getLongTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_HEADERIMG, object.getHeaderImageUrl());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_WEBIMG, object.getWebImageUrl());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_ARTID+"='"+id+ "'", null);
    }

    public List<Art> getAllFavorites() {
        String[] columns = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_ARTID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_HEADERIMG,
                FavoriteContract.FavoriteEntry.COLUMN_WEBIMG
        };

        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<Art> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Art object = new Art();
                object.setId(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ARTID)));
                object.setLongTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                object.setHeaderImageUrl(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_HEADERIMG)));
                object.setWebImageUrl(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_WEBIMG)));

                favoriteList.add(object);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteList;
    }
}
