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

import com.codetech.apson.shop.mvp.model.SearchRepository;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;

import java.util.ArrayList;
import java.util.List;


public class SearchPresenter extends BasePresenter<SearchRepository> {
    private RxErrorHandler mErrorHandler;

    public SearchPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(SearchRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }
    public void getSearchResult(Message message){
        mModel.getSearchResult(message.obj+"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.handleMessageToTarget();
                    }
                })
                .map(new Function<MyBaseResponse<Goods>, List<Goods>>() {
                    @Override
                    public List<Goods> apply(MyBaseResponse<Goods> goodsMyBaseResponse) {
                        return  goodsMyBaseResponse.getRes();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<List<Goods>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Goods> goods) {
                        ArrayList<Goods> list = new ArrayList<>();
                        list.addAll(goods);
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("result" , list);
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