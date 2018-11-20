package com.codetech.apson.shop.mvp.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import com.codetech.apson.shop.mvp.model.AddressChangeRepository;
import com.codetech.apson.shop.mvp.model.entity.BaseResponse;


public class AddressChangePresenter extends BasePresenter<AddressChangeRepository> {
    private RxErrorHandler mErrorHandler;

    public AddressChangePresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(AddressChangeRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }
    public void postAddress(Message message){
        mModel.postAddress(message.objs[0]+"",message.objs[1]+"",message.objs[2]+"",message.objs[3]+"",message.objs[4]+"",
                message.objs[5]+"",message.objs[6]+""
        ,message.objs[7]+"",message.objs[8]+"",message.objs[9]+"",message.objs[10]+"",message.objs[11]+"",message.objs[12]+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.what=3;
                        message.handleMessageToTarget();
                    }
                })
                .map(new Function<BaseResponse<String>, String>() {
                    @Override
                    public String apply(BaseResponse<String> stringBaseResponse) {

                    return  stringBaseResponse.getStatus();

                    }
                })
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(String s) {
                        if(s .equals("200")){
                            message.what=1;
                            message.getTarget().handleMessage(message);
                        }
                        else {
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