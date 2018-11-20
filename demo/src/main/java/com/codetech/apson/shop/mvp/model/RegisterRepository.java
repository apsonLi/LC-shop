package com.codetech.apson.shop.mvp.model;


import com.codetech.apson.shop.mvp.model.api.service.MyUserService;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import me.jessyan.art.mvp.IModel;
import me.jessyan.art.mvp.IRepositoryManager;

/**
 * 必须实现 IModel
 * 可以根据不同的业务逻辑划分多个 Repository 类,多个业务逻辑相近的页面可以使用同一个 Repository 类
 * 无需每个页面都创建一个独立的 Repository
 * 通过 {@link me.jessyan.art.mvp.IRepositoryManager#createRepository(java.lang.Class)} 获得的 Repository 实例,为单例对象
 */
public class RegisterRepository implements IModel {
    private IRepositoryManager mManager;

    public RegisterRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }

    public Observable<BaseResponse<String>> register(String userName, String userPassword){ return mManager.createRetrofitService(MyUserService.class).register(userName,userPassword);}
    @Override
    public void onDestroy() {

    }
}
