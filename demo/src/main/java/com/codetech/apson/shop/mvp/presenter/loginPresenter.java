package com.codetech.apson.shop.mvp.presenter;

import android.os.Bundle;

import io.reactivex.Scheduler;
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

import com.codetech.apson.shop.mvp.model.entity.BaseResponse;
import com.codetech.apson.shop.mvp.model.entity.ClientUser;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;
import com.codetech.apson.shop.mvp.model.loginRepository;


public class loginPresenter extends BasePresenter<loginRepository> {
    private RxErrorHandler mErrorHandler;

    public loginPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(loginRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }
    public void getClientUser(Message message){
        mModel.getClientUser(message.objs[0]+"",message.objs[1]+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.what=2;
                        message.handleMessageToTarget();
                    }
                })
               .map(new Function<BaseResponse<ClientUser>, ClientUser>() {
                   @Override
                   public ClientUser apply(BaseResponse<ClientUser> clientUserMyBaseResponse) {
                       if (clientUserMyBaseResponse.getStatus().equals("200"))
                      return clientUserMyBaseResponse.getRes();
                       return null;
                   }
               })
                .subscribe(new ErrorHandleSubscriber<ClientUser>(mErrorHandler) {
                    @Override
                    public void onNext(ClientUser clientUser) {
                        if(clientUser==null){
                            ArtUtils.snackbarText("用户名密码错误");}
                        message.what=1;
                        Bundle bundle =new Bundle();
                        bundle.putParcelable("clientuser",clientUser);
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