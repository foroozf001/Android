package com.example.a09_gamebacklog_mv;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "backlog_items")
@TypeConverters(GameStatusConverter.class)
public class Game implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "platform")
    private String platform;
    @ColumnInfo(name = "lastUpdated")
    private Long lastUpdated;

    @ColumnInfo(name = "status")
    private GameStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Game(String title, String platform, GameStatus status) {
        this.title = title;
        this.platform = platform;
        this.status = status;

        lastUpdated = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.platform);
        dest.writeLong(this.lastUpdated);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    protected Game(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.platform = in.readString();
        this.lastUpdated = in.readLong();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : GameStatus.values()[tmpStatus];
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
