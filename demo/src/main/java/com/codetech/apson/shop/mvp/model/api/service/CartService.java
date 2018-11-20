package com.codetech.apson.shop.mvp.model.api.service;

import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CartService {
    @FormUrlEncoded
    @POST("getcartout.php")
    Observable<MyBaseResponse<CartinItem>> getCartin(@Field("userID") String userID , @Field("userKEY") String userKEY
            ,@Field("id") String id , @Field("dowhat") String dohat , @Field("goodsID") String goodsID , @Field("name") String name
            ,@Field("img") String img , @Field("price") String price,@Field("add") String add,@Field("status") String status,
            @Field("userPrice") String userPrice);
}
