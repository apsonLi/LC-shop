package com.codetech.apson.shop.mvp.presenter;

import android.util.Log;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

import com.codetech.apson.shop.mvp.model.StartPageRepository;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;
import com.codetech.apson.shop.mvp.model.entity.MyUser;


public class StartPagePresenter extends BasePresenter<StartPageRepository> {
    private RxErrorHandler mErrorHandler;

    public StartPagePresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(StartPageRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }
    public String load_img(){
        Log.e("lcs123","load_img start");
        mModel.getTest("10010","123456","23")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<MyUser>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<MyUser> myUserMyBaseResponse) {

                        Log.e("lcs123",myUserMyBaseResponse.getMsg());
                    }
                });
        return "http://www.sinaimg.cn/dy/slidenews/21_img/2014_40/17327_3739398_140395.jpg";
//        GlideArt.with()
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}