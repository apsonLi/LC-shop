package com.codetech.apson.shop.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    /**
     * address_id : 23
     * user_id : 10010
     * consignee : 陈心琴
     * province : 330000
     * city : 331000
     * district : 331023
     * province_addr : 浙江省
     * city_addr : 台州市
     * district_addr : 天台县
     * street : 3门402
     * zipcode : 331023
     * telephone : 1334444566
     * mobile :
     * defaultAddr : 1
     */

    private String address_id;
    private String user_id;
    private String consignee;
    private String province;
    private String city;
    private String district;
    private String province_addr;
    private String city_addr;
    private String district_addr;
    private String street;
    private String zipcode;
    private String telephone;
    private String mobile;
    private String defaultAddr;

    protected Address(Parcel in) {
        address_id = in.readString();
        user_id = in.readString();
        consignee = in.readString();
        province = in.readString();
        city = in.readString();
        district = in.readString();
        province_addr = in.readString();
        city_addr = in.readString();
        district_addr = in.readString();
        street = in.readString();
        zipcode = in.readString();
        telephone = in.readString();
        mobile = in.readString();
        defaultAddr = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince_addr() {
        return province_addr;
    }

    public void setProvince_addr(String province_addr) {
        this.province_addr = province_addr;
    }

    public String getCity_addr() {
        return city_addr;
    }

    public void setCity_addr(String city_addr) {
        this.city_addr = city_addr;
    }

    public String getDistrict_addr() {
        return district_addr;
    }

    public void setDistrict_addr(String district_addr) {
        this.district_addr = district_addr;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(String defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address_id);
        parcel.writeString(user_id);
        parcel.writeString(consignee);
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeString(district);
        parcel.writeString(province_addr);
        parcel.writeString(city_addr);
        parcel.writeString(district_addr);
        parcel.writeString(street);
        parcel.writeString(zipcode);
        parcel.writeString(telephone);
        parcel.writeString(mobile);
        parcel.writeString(defaultAddr);
    }
}
