package com.codetech.apson.shop.mvp.model.api.service;

import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.model.entity.GoodsInfo;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;


import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoodsService {
    @GET("hotSale.php")
    Observable<MyBaseResponse<Goods>> getGoods();
    @GET("getCategory.php")
    Observable<MyBaseResponse<Classify_Goods>> getClassify_goods ();

    @GET("goodsInfo.php")
    Observable<MyBaseResponse<GoodsInfo>> getGoodsInfo(@Query("goodsid") String goodsid);
    @GET("search.php")
    Observable<MyBaseResponse<Goods>> getSearchResult(@Query("goodsname") String name);
}
