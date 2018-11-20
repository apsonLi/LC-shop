/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codetech.apson.shop.mvp.model.api.service;

import com.codetech.apson.shop.mvp.model.entity.BannerImage;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;
import com.codetech.apson.shop.mvp.model.entity.MyUser;

import io.reactivex.Observable;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ================================================
 * 存放通用的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CommonService {
    @FormUrlEncoded
    @POST("getUserInfo.php")
    Observable<MyBaseResponse<MyUser>> test(@Field("userID") String userID , @Field("userKEY") String userKEY , @Field("id") String id);


    @GET("bannerImage.php")
    Observable<MyBaseResponse<BannerImage>> getBannerImage();
}
