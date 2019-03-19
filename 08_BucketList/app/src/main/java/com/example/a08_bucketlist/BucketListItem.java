package com.example.a08_bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucketlist_table")

public class BucketListItem {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    public BucketListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return this.title; }

    public String getDescription() { return this.description; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "BucketListItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
