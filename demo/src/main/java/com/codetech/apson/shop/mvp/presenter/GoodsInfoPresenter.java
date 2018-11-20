package com.codetech.apson.shop.mvp.presenter;

import android.os.Bundle;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import com.codetech.apson.shop.mvp.model.GoodsInfoRepository;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.model.entity.GoodsInfo;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;


public class GoodsInfoPresenter extends BasePresenter<GoodsInfoRepository> {
    private RxErrorHandler mErrorHandler;

    public GoodsInfoPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(GoodsInfoRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }
    public void getGoodsInfo(Message message){
        message.getTarget().showLoading();
        mModel.getGoodsInfo(message.objs[0]+"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.getTarget().hideLoading();
                        message.handleMessageToTarget();
                    }
                })
                .map(new Function<MyBaseResponse<GoodsInfo>, GoodsInfo>() {
                    @Override
                    public GoodsInfo apply(MyBaseResponse<GoodsInfo> goodsInfoMyBaseResponse) {
                        return goodsInfoMyBaseResponse.getRes().get(0);
                    }
                })
                .subscribe(new ErrorHandleSubscriber<GoodsInfo>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsInfo goodsInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("goodsinfo",goodsInfo);
                        message.what=1;
                        message.setData(bundle);
                        message.getTarget().handleMessage(message);
                    }
                });
    }

    public void addcartin(Message message){
        mModel.addCartin(message.objs[0]+"",message.objs[1]+"",message.objs[0]+"","0",message.objs[2]+"",message.objs[3]+"",message.objs[4]+"",message.objs[5]+"","","",message.objs[5]+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.getTarget().hideLoading();
                        message.handleMessageToTarget();
                    }
                })
                .map(new Function<MyBaseResponse<CartinItem>, String>() {
                    @Override
                    public String apply(MyBaseResponse<CartinItem> cartinItemMyBaseResponse) {
                        return cartinItemMyBaseResponse.getMsg();
                    }
                }).subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
            @Override
            public void onNext(String s) {
                if(s.equals("200")){
                    message.what=0;
                    message.getTarget().handleMessage(message);
                }
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}