package com.codetech.apson.shop.mvp.presenter;

import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import com.codetech.apson.shop.mvp.model.PayRepository;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;

import java.util.ArrayList;
import java.util.List;


public class PayPresenter extends BasePresenter<PayRepository> {
    private RxErrorHandler mErrorHandler;

    public PayPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(PayRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }

    public void pay(Message message){
        mModel.pay(message.objs[0]+"",message.objs[1]+"")
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if(stringBaseResponse.getStatus().equals("200")){
                            message.what=1;
                            message.getTarget().handleMessage(message);
                        }
                        else {
                            message.what=0;
                            message.getTarget().handleMessage(message);
                        }
                    }
                });
    }


    public void getrealAddresss(Message message){
          mModel.getAddress(message.objs[0]+"",message.objs[0]+"",message.objs[1]+"")
                .subscribeOn(Schedulers.io())
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
                }).flatMap(new Function<ArrayList<Address>, Observable<Address>>() {
                    @Override
                    public Observable<Address> apply(ArrayList<Address> addresses) throws Exception {
                        return Observable.fromIterable(addresses);
                    }
                })
                .filter(new Predicate<Address>() {
                    @Override
                    public boolean test(Address address) throws Exception {
                        return address.getDefaultAddr().equals("1");
                    }
                })
                  .toList()
                  .flatMapObservable(new Function<List<Address>, Observable<Address>>() {
                      @Override
                      public Observable<Address> apply(List<Address> addresses) throws Exception {
                          return Observable.just(addresses.get(0));
                      }
                  })
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new ErrorHandleSubscriber<Address>(mErrorHandler) {
                      @Override
                      public void onNext(Address address) {
                          message.what =3;
                          Bundle bundle =new Bundle();
                          bundle.putParcelable("result",address);
                          message.setData(bundle);
                          message.getTarget().handleMessage(message);
                      }
                  });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}