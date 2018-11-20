package com.codetech.apson.shop.mvp.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import com.codetech.apson.shop.mvp.model.RegisterRepository;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;


public class RegisterPresenter extends BasePresenter<RegisterRepository> {
    private RxErrorHandler mErrorHandler;

    public RegisterPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(RegisterRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }
    public void register(Message message){
        mModel.register(message.getData().getString("username"),message.getData().getString("userpwd"))
                .subscribeOn(Schedulers.io())
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
                        if(stringBaseResponse.getStatus() .equals("200")){
                            message.what=1;
                            message.getTarget().handleMessage(message);
                        }
                        if(stringBaseResponse.getStatus() .equals("408")){
                            message.what=2;
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