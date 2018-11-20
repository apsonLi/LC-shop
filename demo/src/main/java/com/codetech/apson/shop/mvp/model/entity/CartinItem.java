package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CartinItem implements Parcelable {

    /**
     * goods_id : 100020001
     * status : 1
     * goods_img : http://www.wanheinfo.com/HOME/image/100020001/20161217000529717.jpg
     * goods_name : 人参多肽赋活洁面霜
     * market_price : 0.00
     * goods_price : 298.00
     * goods_number : 2
     */

    private String goods_id;
    private String status;
    private String goods_img;
    private String goods_name;
    private String market_price;
    private String goods_price;
    private String goods_number;

    protected CartinItem(Parcel in) {
        goods_id = in.readString();
        status = in.readString();
        goods_img = in.readString();
        goods_name = in.readString();
        market_price = in.readString();
        goods_price = in.readString();
        goods_number = in.readString();
    }

    public static final Creator<CartinItem> CREATOR = new Creator<CartinItem>() {
        @Override
        public CartinItem createFromParcel(Parcel in) {
            return new CartinItem(in);
        }

        @Override
        public CartinItem[] newArray(int size) {
            return new CartinItem[size];
        }
    };

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goods_id);
        parcel.writeString(status);
        parcel.writeString(goods_img);
        parcel.writeString(goods_name);
        parcel.writeString(market_price);
        parcel.writeString(goods_price);
        parcel.writeString(goods_number);
    }
}
