package com.codetech.apson.shop.mvp.presenter;

import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import com.codetech.apson.shop.mvp.model.AddressRepository;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;

import java.util.ArrayList;



public class AddressPresenter extends BasePresenter<AddressRepository> {
    private RxErrorHandler mErrorHandler;

    public AddressPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(AddressRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }

    public void getAddress(Message message){
        mModel.getAddress(message.objs[0]+"",message.objs[0]+"",message.objs[1]+"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.getTarget().hideLoading();
                        message.handleMessageToTarget();
                    }
                })
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .map(new Function<MyBaseResponse<Address>, ArrayList<Address>>() {


                    @Override
                    public ArrayList<Address> apply(MyBaseResponse<Address> addressMyBaseResponse) {
                        ArrayList<Address> list = new ArrayList<>();
                        list.addAll(addressMyBaseResponse.getRes());
                        return list;
                    }
                })
                .subscribe(new ErrorHandleSubscriber<ArrayList<Address>>(mErrorHandler) {
                    @Override
                    public void onNext(ArrayList<Address> addresses) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("addresslist",addresses);
                        message.setData(bundle);
                        message.what =1 ;
                        message.getTarget().handleMessage(message);
                    }
                });
    }
    public Observable<ArrayList<Address>> getrealAddresss(Message message){
       return  mModel.getAddress(message.objs[0]+"",message.objs[0]+"",message.objs[1]+"")
               .doFinally(new Action() {
                   @Override
                   public void run() throws Exception {
                       message.handleMessageToTarget();
                   }
               })
               .doOnSubscribe(disposable -> {addDispose(disposable);})
               .map(new Function<MyBaseResponse<Address>, ArrayList<Address>>() {
           @Override
           public ArrayList<Address> apply(MyBaseResponse<Address> addressMyBaseResponse) throws Exception {
               ArrayList<Address> list = new ArrayList<>();
               list.addAll(addressMyBaseResponse.getRes());
               return list;
           }
       });


    }
    public void  setDefaultAddress(Message message){
        mModel.setDefaultAddress(message.objs[0]+"",message.objs[1]+"",message.objs[0]+"",message.objs[2]+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if(stringBaseResponse.getStatus().equals("200")){
                            ArtUtils.snackbarText("设置默认地址成功");
                            getAddress(message);
                        }
                    }
                });
    }


    public void delAddress(Message message){
        mModel.delAddress(message.objs[0]+"",message.objs[1]+"",message.objs[2]+"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.handleMessageToTarget();
                    }
                })
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if(stringBaseResponse.getStatus().equals("200"))
                        {
                            message.what =0;
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