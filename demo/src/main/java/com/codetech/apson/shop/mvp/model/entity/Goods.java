package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods implements Parcelable{

    /**
     * goods_id : 100010001
     * goods_img : image/100010001/20161217000018247.jpg
     * goods_name : 人参多肽赋活面膜
     * shop_price : 398.20
     * sold : 1157
     */

    private String goods_id;
    private String goods_img;
    private String goods_name;
    private String shop_price;
    private String sold;

    protected Goods(Parcel in) {
        goods_id = in.readString();
        goods_img = in.readString();
        goods_name = in.readString();
        shop_price = in.readString();
        sold = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goods_id);
        parcel.writeString(goods_img);
        parcel.writeString(goods_name);
        parcel.writeString(shop_price);
        parcel.writeString(sold);
    }
}
