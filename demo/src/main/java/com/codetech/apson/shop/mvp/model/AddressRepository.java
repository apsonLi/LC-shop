package com.codetech.apson.shop.mvp.model;

import com.codetech.apson.shop.mvp.model.api.service.AddressService;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;
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
public class AddressRepository implements IModel {
    private IRepositoryManager mManager;

    public AddressRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }

    public Observable<MyBaseResponse<Address>> getAddress(String id,String userID,String userKEY){
        return mManager.createRetrofitService(AddressService.class).getAddress(id,userID,userKEY);
    }

    public Observable<BaseResponse<String>> delAddress(String id,String userID,String userKEY){
        return mManager.createRetrofitService(AddressService.class).delAddress(id,userID,userKEY);
    }

    public Observable<BaseResponse<String>> setDefaultAddress(String userID,String userKEY,String id,String addrid){
        return mManager.createRetrofitService(AddressService.class).setDefaultAddress(userID,userKEY,id,addrid);
    }

    @Override
    public void onDestroy() {

    }
}
