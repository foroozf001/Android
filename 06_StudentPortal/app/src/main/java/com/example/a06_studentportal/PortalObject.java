package com.example.a06_studentportal;

import android.os.Parcel;
import android.os.Parcelable;

public class PortalObject implements Parcelable {
    private String mPortalLink;
    private String mPortalName;

    public String getmPortalLink() {
        return mPortalLink;
    }

    public String getmPortalName() {
        return mPortalName;
    }

    public void setmPortalLink(String mPortalLink) {
        this.mPortalLink = mPortalLink;
    }

    public void setmPortalName(String mPortalName) {
        this.mPortalName = mPortalName;
    }

    public PortalObject(String mPortalLink, String mPortalName) {
        this.mPortalLink = mPortalLink;
        this.mPortalName = mPortalName;
    }

    @Override
    public String toString() {
        return "[Portal name: " + this.mPortalName + ", Portal link: " + this.mPortalLink + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPortalLink);
        dest.writeString(this.mPortalName);
    }

    protected PortalObject(Parcel in) {
        this.mPortalLink = in.readString();
        this.mPortalName = in.readString();
    }

    public static final Parcelable.Creator<PortalObject> CREATOR = new Parcelable.Creator<PortalObject>() {
        @Override
        public PortalObject createFromParcel(Parcel source) {
            return new PortalObject(source);
        }

        @Override
        public PortalObject[] newArray(int size) {
            return new PortalObject[size];
        }
    };
}
