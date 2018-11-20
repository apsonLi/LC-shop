package com.codetech.apson.shop.mvp.model.api.service;

import com.codetech.apson.shop.mvp.model.entity.BaseResponse;
import com.codetech.apson.shop.mvp.model.entity.ClientUser;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyUserService {
    @FormUrlEncoded
    @POST("login.php")
    Observable<BaseResponse<ClientUser>> getClientUser(@Field("userName") String userName, @Field("userPassword") String userPassword);

    @FormUrlEncoded
    @POST("register.php")
    Observable<BaseResponse<String>> register(@Field("userName") String userName, @Field("userPassword") String userPassword);

    @FormUrlEncoded
    @POST("getNicknameByID")
    Observable<MyBaseResponse<String>> getname(@Field("userID") String userID, @Field("userKEY") String userKEY,@Field("id")String id);
}
