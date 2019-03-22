package com.example.a09_gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.text.SimpleDateFormat;

@Entity(tableName = "GameBacklog_Table")
public class Game implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "platform")
    private String platform;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "date")
    private String date;

    public Game(String title, String platform, String status) {
        setTitle(title);
        setPlatform(platform);
        setStatus(status);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setDate(sdf.format(new Date()));
    }

    protected Game(Parcel in) {
        this.title = in.readString();
        this.platform = in.readString();
        this.status = in.readString();
        this.date = in.readString();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Game{ " +
                "id = " + id + '\'' +
                ", title = " + title + '\'' +
                ", platform = " + platform + '\'' +
                ", status = " + status + '\'' +
                ", date = " + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.platform);
        dest.writeString(this.status);
        dest.writeString(this.date);
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
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
