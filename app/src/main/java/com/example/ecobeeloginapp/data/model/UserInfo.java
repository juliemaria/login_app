package com.example.ecobeeloginapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {

    // Hardcoded the displayName -> expect to receive from DB
    private String displayName = "User 1";
    private String userId = "user1";

    public UserInfo() {}

    public UserInfo(Parcel in) {
        displayName = in.readString();
        userId = in.readString();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(userId);
    }
}
