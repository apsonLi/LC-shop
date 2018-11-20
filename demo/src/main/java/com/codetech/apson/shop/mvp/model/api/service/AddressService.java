package com.codetech.apson.shop.mvp.model.api.service;

import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddressService {
    @FormUrlEncoded
    @POST("getaddr.php")
    Observable<MyBaseResponse<Address>> getAddress(@Field("id") String id,@Field("userID") String userID,@Field("userKEY") String userKEY);

    @FormUrlEncoded
    @POST("deladdr.php")
    Observable<BaseResponse<String>> delAddress(@Field("addrid") String id ,@Field("userID" )String userID,@Field("userKEY")String userKEY);

    @FormUrlEncoded
    @POST("postaddr.php")
    Observable<BaseResponse<String>> postAddress(@Field("userID" )String userID,@Field("userKEY")String userKEY,@Field("addtype")String addtype,@Field("id")String id,@Field("name")String name
            ,@Field("tel")String tel,@Field("province")String provice
            ,@Field("city")String city,@Field("county")String county
            ,@Field("provinceCode")String provinceCode,@Field("cityCode")String cityCode,@Field("countyCode")String countyCode
            ,@Field("moreAddr")String moreAddr);

    @FormUrlEncoded
    @POST("setdefault.php")
    Observable<BaseResponse<String>> setDefaultAddress(@Field("userID" )String userID,@Field("userKEY")String userKEY,@Field("id") String id
                        ,@Field("addrid") String addrid);
}
