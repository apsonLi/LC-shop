package com.codetech.apson.shop.mvp.model;

import android.util.Log;

import com.codetech.apson.shop.mvp.model.api.service.AddressService;
import com.codetech.apson.shop.mvp.model.api.service.CartService;
import com.codetech.apson.shop.mvp.model.api.service.CommonService;
import com.codetech.apson.shop.mvp.model.api.service.GoodsService;
import com.codetech.apson.shop.mvp.model.api.service.MyUserService;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.BannerImage;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;

import java.util.List;


import io.reactivex.Observable;
import me.jessyan.art.mvp.IModel;
import me.jessyan.art.mvp.IRepositoryManager;
import me.jessyan.art.utils.ArtUtils;

/**
 * 必须实现 IModel
 * 可以根据不同的业务逻辑划分多个 Repository 类,多个业务逻辑相近的页面可以使用同一个 Repository 类
 * 无需每个页面都创建一个独立的 Repository
 * 通过 {@link me.jessyan.art.mvp.IRepositoryManager#createRepository(java.lang.Class)} 获得的 Repository 实例,为单例对象
 */
public class MainRepository implements IModel {
    private IRepositoryManager mManager;

    public MainRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }
    public Observable<MyBaseResponse<BannerImage>> getImage(){
//        Log.e("mmodel.getImage","过去");

        return mManager.createRetrofitService(CommonService.class).getBannerImage();
    }
    public Observable<MyBaseResponse<Goods>> getHotSale(){
        return mManager.createRetrofitService(GoodsService.class).getGoods();
    }
    public Observable<MyBaseResponse<Classify_Goods>> getClassifyGoods(){
        return mManager.createRetrofitService(GoodsService.class).getClassify_goods();
    }
    public Observable<MyBaseResponse<CartinItem>> getCartin( String userID ,  String userKEY,
             String id , String dowhat , String goodsID , String name
            , String img , String price, String add, String status, String userPrice){
        return mManager.createRetrofitService(CartService.class).getCartin(userID,userKEY,id,dowhat,goodsID,name,img,price,add,status,userPrice);
    }

    public Observable<MyBaseResponse<Address>> getAddress(String id, String userID, String userKEY){
        return mManager.createRetrofitService(AddressService.class).getAddress(id,userID,userKEY);
    }

    public Observable<MyBaseResponse<String>> getName(String userid,String userkey,String id){
        return  mManager.createRetrofitService(MyUserService.class).getname(userid,userkey,id);
    }

    @Override
    public void onDestroy() {

    }
}
