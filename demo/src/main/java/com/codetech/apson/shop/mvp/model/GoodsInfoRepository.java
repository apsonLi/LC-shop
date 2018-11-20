package com.codetech.apson.shop.mvp.model;

import com.codetech.apson.shop.mvp.model.api.service.CartService;
import com.codetech.apson.shop.mvp.model.api.service.GoodsService;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.model.entity.GoodsInfo;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;

import io.reactivex.Observable;
import me.jessyan.art.mvp.IModel;
import me.jessyan.art.mvp.IRepositoryManager;

/**
 * 必须实现 IModel
 * 可以根据不同的业务逻辑划分多个 Repository 类,多个业务逻辑相近的页面可以使用同一个 Repository 类
 * 无需每个页面都创建一个独立的 Repository
 * 通过 {@link me.jessyan.art.mvp.IRepositoryManager#createRepository(java.lang.Class)} 获得的 Repository 实例,为单例对象
 */
public class GoodsInfoRepository implements IModel {
    private IRepositoryManager mManager;

    public GoodsInfoRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }

    public Observable<MyBaseResponse<GoodsInfo>> getGoodsInfo(String goodsid){
       return  mManager.createRetrofitService(GoodsService.class).getGoodsInfo(goodsid);
    }
    public Observable<MyBaseResponse<CartinItem>> addCartin(String userID , String userKEY,
                                                            String id , String dowhat , String goodsID , String name
            , String img , String price, String add, String status, String userPrice){
        return mManager.createRetrofitService(CartService.class).getCartin(userID,userKEY,id,dowhat,goodsID,name,img,price,add,status,userPrice);
    }
    @Override
    public void onDestroy() {

    }
}
