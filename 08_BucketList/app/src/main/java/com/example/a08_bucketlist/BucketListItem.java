package com.example.a08_bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "bucketlist_table")

public class BucketListItem implements Parcelable {

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

    protected BucketListItem(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
    }

    public static final Parcelable.Creator<BucketListItem> CREATOR = new Parcelable.Creator<BucketListItem>() {
        @Override
        public BucketListItem createFromParcel(Parcel source) {
            return new BucketListItem(source);
        }

        @Override
        public BucketListItem[] newArray(int size) {
            return new BucketListItem[size];
        }
    };
}
