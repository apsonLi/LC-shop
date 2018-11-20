package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Classify_Goods implements Parcelable {

    /**
     * id : 1
     * dad : 面膜系列
     * child : [{"goods_img":"image/100010001/20161217000018247.jpg","goods_name":"这破玩意名字咋这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长捏","goods_id":"100010001"}]
     */

    private String id;
    private String dad;
    private List<ChildBean> child;

    protected Classify_Goods(Parcel in) {
        id = in.readString();
        dad = in.readString();
        child = in.createTypedArrayList(ChildBean.CREATOR);
    }

    public static final Creator<Classify_Goods> CREATOR = new Creator<Classify_Goods>() {
        @Override
        public Classify_Goods createFromParcel(Parcel in) {
            return new Classify_Goods(in);
        }

        @Override
        public Classify_Goods[] newArray(int size) {
            return new Classify_Goods[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDad() {
        return dad;
    }

    public void setDad(String dad) {
        this.dad = dad;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(dad);
        parcel.writeTypedList(child);
    }

    public static class ChildBean implements Parcelable {
        /**
         * goods_img : image/100010001/20161217000018247.jpg
         * goods_name : 这破玩意名字咋这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长这么长捏
         * goods_id : 100010001
         */


        private String goods_img;
        private String goods_name;
        private String goods_id;

        protected ChildBean(Parcel in) {
            goods_img = in.readString();
            goods_name = in.readString();
            goods_id = in.readString();
        }

        public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
            @Override
            public ChildBean createFromParcel(Parcel in) {
                return new ChildBean(in);
            }

            @Override
            public ChildBean[] newArray(int size) {
                return new ChildBean[size];
            }
        };

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

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(goods_img);
            parcel.writeString(goods_name);
            parcel.writeString(goods_id);
        }
    }
}
