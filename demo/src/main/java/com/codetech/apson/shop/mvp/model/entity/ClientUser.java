package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ClientUser implements Parcelable {
    String userID;
    String userKEY;

    protected ClientUser(Parcel in) {
        userID = in.readString();
        userKEY = in.readString();
    }

    public static final Creator<ClientUser> CREATOR = new Creator<ClientUser>() {
        @Override
        public ClientUser createFromParcel(Parcel in) {
            return new ClientUser(in);
        }

        @Override
        public ClientUser[] newArray(int size) {
            return new ClientUser[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserKEY() {
        return userKEY;
    }

    public void setUserKEY(String userKEY) {
        this.userKEY = userKEY;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userID);
        parcel.writeString(userKEY);
    }
}
