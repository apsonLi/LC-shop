package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BannerImage implements Parcelable {

    /**
     * image : bannerImage/20161215205901437.jpg
     * id : 100030001
     */

    private String image;
    private String id;

    protected BannerImage(Parcel in) {
        image = in.readString();
        id = in.readString();
    }

    public static final Creator<BannerImage> CREATOR = new Creator<BannerImage>() {
        @Override
        public BannerImage createFromParcel(Parcel in) {
            return new BannerImage(in);
        }

        @Override
        public BannerImage[] newArray(int size) {
            return new BannerImage[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(id);
    }
}
