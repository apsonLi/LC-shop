package com.codetech.apson.shop.mvp.presenter;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import com.codetech.apson.shop.mvp.model.MainRepository;
import com.codetech.apson.shop.mvp.model.api.Api;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.BannerImage;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;
import com.codetech.apson.shop.mvp.ui.fragment.CartinFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



public class MainPresenter extends BasePresenter<MainRepository> {
    private RxErrorHandler mErrorHandler;
    private CartinFragment view  =new CartinFragment();//不建议这么写 ， 其实更好的办法是直接将 adapter 注入进来
    public MainPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(MainRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }

    public void getBannerImage(Message msg){

        mModel.getImage().subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MyBaseResponse<BannerImage>, List<BannerImage>>() {
                    @Override
                    public List<BannerImage> apply(MyBaseResponse<BannerImage> bannerImageMyBaseResponse) {
                        return bannerImageMyBaseResponse.getRes();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
              .subscribe(new ErrorHandleSubscriber<List<BannerImage>>(mErrorHandler) {
                  @Override
                  public void onNext(List<BannerImage> bannerImages) {
                      ArrayList<String>  banner_image = new ArrayList<>();
                      ArrayList<String>  banner_iamge_id = new ArrayList<>();
                        for(BannerImage bannerImage : bannerImages){
                            banner_image.add(Api.APP_DOMAIN+bannerImage.getImage());
                            banner_iamge_id.add(bannerImage.getId());
                        }

                      msg.what=0;
                      Bundle bundle =new Bundle();
                      bundle.putStringArrayList("banner_image",banner_image);
                      bundle.putStringArrayList("banner_image_id",banner_iamge_id);
                      msg.setData(bundle);
                      msg.getTarget().handleMessage(msg);
                  }
              });
//                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<BannerImage>>(mErrorHandler) {
//                    @Override
//                    public void onNext(MyBaseResponse<BannerImage> bannerImageMyBaseResponse) {
//
//                    }
//                })

    }
    public void getClassify(Message message){
        mModel.getClassifyGoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    addDispose(disposable);
                })
                .map(new Function<MyBaseResponse<Classify_Goods>, List<Classify_Goods>>() {
                    @Override
                    public List<Classify_Goods> apply(MyBaseResponse<Classify_Goods> classify_goodsMyBaseResponse) {
                        return classify_goodsMyBaseResponse.getRes();
                    }
                })


                .subscribe(new ErrorHandleSubscriber<List<Classify_Goods>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Classify_Goods> classify_goods) {
                        ArrayList<Classify_Goods> classify_goods1 =  new ArrayList<>();
                        classify_goods1.addAll(classify_goods);
                        message.what = 4;
                        Bundle bundle = new Bundle();bundle.putParcelableArrayList("classify_goods" , classify_goods1);
                        message.setData(bundle);
                        message.handleMessageToTarget();
                    }
                });

    }

    public  void getHotSale(Message message){
        mModel.getHotSale()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MyBaseResponse<Goods>, List<Goods>>() {
                    @Override
                    public List<Goods> apply(MyBaseResponse<Goods> goodsMyBaseResponse) {
                        return goodsMyBaseResponse.getRes();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.what=3;
                        message.handleMessageToTarget();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<List<Goods>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Goods> goods) {
                        ArrayList<Goods> list =new ArrayList<>();
                        for(Goods g: goods){
                            list.add(g);
                        }
                        message.what=1;
                        Bundle bundle =new Bundle();
                        bundle.putParcelableArrayList("hotSale_list",list);
                        message.setData(bundle);
                        message.getTarget().handleMessage(message);
                    }
                });

    }

    public void getCartin(Message message){

        mModel.getCartin(message.objs[0]+"",message.objs[1]+"",message.objs[0]+"","3","","","","","","","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        message.getTarget().hideLoading();
                        message.what =1;
                        message.handleMessageToTarget();
                    }
                })
               .map(new Function<MyBaseResponse<CartinItem>, ArrayList<CartinItem>>() {
                   @Override
                   public ArrayList<CartinItem> apply(MyBaseResponse<CartinItem> cartinItemMyBaseResponse) {
                       if(cartinItemMyBaseResponse.getStatus().equals("200")){
                           ArrayList<CartinItem> list  =new ArrayList<>();
                           list.addAll(cartinItemMyBaseResponse.getRes());
                           return list;
                       }
                      return null;
                   }
               })
                .subscribe(new ErrorHandleSubscriber<ArrayList<CartinItem>>(mErrorHandler) {
                    @Override
                    public void onNext(ArrayList<CartinItem> cartinItems) {
                        if(cartinItems == null){
                            message.what = 6;
                            message.getTarget().handleMessage(message);
                        }
                        message.what = 5 ;
                        Bundle bundle =new Bundle();
                        bundle.putParcelableArrayList("cartin_list",cartinItems);
                        message.setData(bundle);
                        message.getTarget().handleMessage(message);
                    }
                });
    }

    public Observable<List<CartinItem>> refreshcartin(String userID ,  String userKEY,
                                                           String id , String dowhat , String goodsID , String name
            , String img , String price, String add, String status, String userPrice) {

       return  mModel.getCartin(userID, userKEY, id, dowhat, goodsID, name, img, price, add, status, userPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    addDispose(disposable);
                })
                .map(new Function<MyBaseResponse<CartinItem>, MyBaseResponse<CartinItem>>() {
                    @Override
                    public MyBaseResponse<CartinItem> apply(MyBaseResponse<CartinItem> cartinItemMyBaseResponse) {
                        if (!cartinItemMyBaseResponse.getStatus().equals("200"))
                            ArtUtils.snackbarText("获取失败");
                        return cartinItemMyBaseResponse;
                    }
                })

                .observeOn(Schedulers.io())
                .flatMap(new Function<MyBaseResponse<CartinItem>, ObservableSource<MyBaseResponse<CartinItem>>>() {
                    @Override
                    public ObservableSource<MyBaseResponse<CartinItem>> apply(MyBaseResponse<CartinItem> cartinItemMyBaseResponse) {
                        return mModel.getCartin(userID, userKEY, id, "3", goodsID, name, img, price, add, status, userPrice);
                    }
                }).map(new Function<MyBaseResponse<CartinItem>, List<CartinItem>>() {
                   @Override
                   public List<CartinItem> apply(MyBaseResponse<CartinItem> cartinItemMyBaseResponse) throws Exception {
                       if(cartinItemMyBaseResponse !=null)
                       return cartinItemMyBaseResponse.getRes();
                       return null;
                   }
               });

    }

    public Observable<List<CartinItem>> getRealCartin(String userID ,  String userKEY){

        return mModel.getCartin(userID,userKEY,userID,"3","","","","","","","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {addDispose(disposable);})
                .observeOn(Schedulers.io())
                .map(new Function<MyBaseResponse<CartinItem>, List<CartinItem>>() {
                    @Override
                    public List<CartinItem> apply(MyBaseResponse<CartinItem> cartinItemMyBaseResponse) {
                        return  cartinItemMyBaseResponse.getRes();
                    }
                });
    }
    public Observable<MyBaseResponse<String>> getName(String userid,String userkey,String id) {
        return mModel.getName(userid,userkey,id);

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}