package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsInfo implements Parcelable {

    /**
     * goods_name : 这破玩意名字咋这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长捏
     * shop_price : 398.20
     * sold : 1157
     * goods_img : image/100010001/20161217000018247.jpg
     * goods_brief :
     */


    private String goods_name;
    private String shop_price;
    private String sold;
    private String goods_img;
    private String goods_brief;

    protected GoodsInfo(Parcel in) {
        goods_name = in.readString();
        shop_price = in.readString();
        sold = in.readString();
        goods_img = in.readString();
        goods_brief = in.readString();
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        @Override
        public GoodsInfo createFromParcel(Parcel in) {
            return new GoodsInfo(in);
        }

        @Override
        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };

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

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goods_name);
        parcel.writeString(shop_price);
        parcel.writeString(sold);
        parcel.writeString(goods_img);
        parcel.writeString(goods_brief);
    }
}
