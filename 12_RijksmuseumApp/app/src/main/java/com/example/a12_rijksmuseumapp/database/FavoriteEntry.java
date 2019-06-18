package com.example.a12_rijksmuseumapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favoritetable")
public class FavoriteEntry {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "artid")
    private String artid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "headerImg")
    private String headerImg;

    @ColumnInfo(name = "webImg")
    private String webImg;

    @Ignore
    public FavoriteEntry(String artid, String title, String headerImg, String webImg) {
        this.artid = artid;
        this.title = title;
        this.headerImg = headerImg;
        this.webImg = webImg;
    }

    public FavoriteEntry(int id, String artid, String title, String headerImg, String webImg) {
        this.id = id;
        this.artid = artid;
        this.title = title;
        this.headerImg = headerImg;
        this.webImg = webImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtid() {
        return this.artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getWebImg() {
        return webImg;
    }

    public void setWebImg(String webImg) {
        this.webImg = webImg;
    }
}