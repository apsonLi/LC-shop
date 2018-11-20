package com.codetech.apson.shop.mvp.model.api.service;



import com.codetech.apson.shop.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PayService {

    @FormUrlEncoded
    @POST("pay.php")
    Observable<BaseResponse<String>>  pay(@Field("userID") String userID,@Field("userKEY") String userKEY,@Field("id")String id,@Field("status") String status);
}
