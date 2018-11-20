package com.codetech.apson.shop.mvp.ui.holder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.app.utils.MyUtils;
import com.codetech.apson.shop.mvp.model.api.Api;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;
import com.codetech.apson.shop.mvp.ui.activity.GoodsInfoActivity;
import com.codetech.apson.shop.mvp.ui.adapter.CartinAdapter;
import com.codetech.apson.shop.mvp.ui.fragment.CartinFragment;
import com.codetech.apson.shop.mvp.ui.view.dialog.CenterDialog;
import com.codetech.apson.shop.mvp.ui.view.dialog.Goods_dialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class CartinItemHolder extends BaseHolder<CartinItem> {

    @BindView(R.id.goods_name)
    TextView goods_name;
    @BindView(R.id.goods_img)
    ImageView goods_img;
    @BindView(R.id.cartin_select)
    ImageView isSelect;
    @BindView(R.id.goods_price)
    TextView goods_price;
    @BindView(R.id.goods_number)
    TextView goods_number;
    @BindView(R.id.goods_add)
    ImageView  goods_add;
    @BindView(R.id.goods_reduce)
    ImageView goods_reduce;

    MainPresenter mainPresenter;
    private final SharedPreferences sharedPreferences;
    CartinFragment cartinFragment;
    CartinAdapter cartinAdapter;
    private final AppComponent appComponent;
    private final String userID;
    private final String userKEY;

    public CartinItemHolder(View itemView, MainPresenter presenter , CartinFragment cartinFragment, CartinAdapter cartinAdapter) {
        super(itemView);
        this.mainPresenter = presenter;
        appComponent = ArtUtils.obtainAppComponentFromContext(itemView.getContext());
        sharedPreferences = appComponent.application().getSharedPreferences("userinfo",0);
        userID = sharedPreferences.getString("userID",null);
        userKEY = sharedPreferences.getString("userKEY",null);
        this.cartinFragment = cartinFragment;
        this.cartinAdapter =cartinAdapter;
    }

    @Override
    public void setData(CartinItem data, int position) {

        if(data.getStatus() .equals("1") )
            GlideArt.with(itemView).load(R.mipmap.cartin_select).into(isSelect);
        else GlideArt.with(itemView).load(R.mipmap.cartin_not_select).into(isSelect);
        GlideArt.with(itemView).load(Api.APP_DOMAIN+data.getGoods_img()).placeholder(R.mipmap.art_logo).error(R.drawable.error).into(goods_img);
        goods_name.setText(data.getGoods_name());
        goods_price.setText("¥"+data.getGoods_price());
        goods_number.setText(data.getGoods_number());
        //开始监听
        isSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cartinFragment.showLoading();
                if(userID ==null|| userKEY==null){return;}
                if(data.getStatus() .equals("1") ){

                    mainPresenter.refreshcartin(userID,userKEY,userID,"2",data.getGoods_id(),"","","","0","2","")
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {
                                    Log.e("cartinitemholder" , "dofinally");
                                    cartinFragment.hideLoading();
                                }
                            })
                            .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(appComponent.rxErrorHandler()) {
                                @Override
                                public void onNext(List<CartinItem> list) {
                                    cartinFragment.setSelect_all(list);
                                    cartinFragment.setSum(list);
                                    cartinAdapter.update(list);
                                }
                            });
//                    mainPresenter.getCartin(Message.obtain(cartinFragment,  new Object[]{userID,userKEY}));

                }
                else{
                    mainPresenter.refreshcartin(userID,userKEY,userID,"2",data.getGoods_id(),"","","","0","1","")
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {
                                    Log.e("cartinitemholder" , "dofinally");
                                    cartinFragment.hideLoading();
                                }
                            })
                            .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(appComponent.rxErrorHandler()) {
                                @Override
                                public void onNext(List<CartinItem> list) {
                                    cartinFragment.setSelect_all(list);
                                    cartinFragment.setSum(list);
                                    cartinAdapter.update(list);
                                }
                            });
                }


            }
        });

        goods_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CenterDialog centerDialog = cartinFragment.getDialog();
                if(centerDialog != null){
                   centerDialog.show();
                    centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                        @Override
                        public void OnCenterItemClick(CenterDialog dialog, View view) {
                            if(view.getId() == R.id.dialog_sure){
                                cartinFragment.showLoading();
                                mainPresenter.refreshcartin(userID,userKEY,userID,"1",data.getGoods_id(),"","","","","","")
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .doFinally(new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                cartinFragment.hideLoading();
                                            }
                                        })
                                        .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(ArtUtils.obtainAppComponentFromContext(cartinFragment.getContext()).rxErrorHandler()) {
                                            @Override
                                            public void onNext(List<CartinItem> list) {
                                                if(cartinFragment.getContext()!=null && cartinFragment.getSelect_all()!=null && cartinFragment.getCartin_sum() !=null){
                                                    MyUtils.tip(list,cartinFragment.getCartin_tip(),cartinFragment.getCartin_pay_layout());
                                                    cartinFragment.getSelect_all().setImageResource( MyUtils.isCartrinSelectAll(list,cartinFragment.getSelect_all()) ? R.mipmap.cartin_select : R.mipmap.cartin_not_select);
                                                    cartinFragment.getCartin_sum().setText(String.format(cartinFragment.getResources().getString(R.string.sum),MyUtils.sumCartin(list)));
                                                }
                                                cartinAdapter.update(list);
                                            }
                                        });
                            }
                        }
                    });
                }


                return true;
            }
        });
        goods_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userID ==null|| userKEY==null){return;}
                if(cartinFragment.getContext() == null) return;
                Intent intent = new Intent(cartinFragment.getContext(), GoodsInfoActivity.class);
                intent.putExtra("goodsid",data.getGoods_id());
                cartinFragment.getContext().startActivity(intent);
            }
        });
        goods_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(Integer.parseInt(data.getGoods_number()) < 200 ){

                    cartinFragment.showLoading();
                    mainPresenter.refreshcartin(userID,userKEY,userID,"2",data.getGoods_id(),"","","","-2","","")
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {
                                    Log.e("cartinitemholder" , "dofinally");
                                    cartinFragment.hideLoading();
                                }
                            })
                            .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(appComponent.rxErrorHandler()) {
                                @Override
                                public void onNext(List<CartinItem> list) {
                                    cartinFragment.setSum(list);
                                    cartinAdapter.update(list);
                                }
                            });
                }

            }
        });
        goods_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(data.getGoods_number()) > 1  ){
                    cartinFragment.showLoading();
                    mainPresenter.refreshcartin(userID,userKEY,userID,"2",data.getGoods_id(),"","","","-1","","")
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {

                                    cartinFragment.hideLoading();
                                }
                            })
                            .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(appComponent.rxErrorHandler()) {
                                @Override
                                public void onNext(List<CartinItem> list) {
                                    cartinFragment.setSum(list);
                                    cartinAdapter.update(list);
                                }
                            });
                }

            }
        });
        goods_number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                cartinFragment.getNumberDialog().show();
                cartinFragment.getNumberDialog().setNumber_input(data.getGoods_number());
                    cartinFragment.getNumberDialog().setGoodsOnClickListener(new Goods_dialog.GoodsOnClickListener() {
                        @Override
                        public void goodsOnItemClick(View view) {

                            if(view.getId() == R.id.dialog_sure) {
                                if (cartinFragment.getNumberDialog().getNumber_input() != null && 1 <= Integer.parseInt(cartinFragment.getNumberDialog().getNumber_input()) && Integer.parseInt(cartinFragment.getNumberDialog().getNumber_input()) <= 200) {
                                    cartinFragment.showLoading();
                                    mainPresenter.refreshcartin(userID, userKEY, userID, "2", data.getGoods_id(), "", "", "", data.getGoods_number().equals(cartinFragment.getNumberDialog().getNumber_input()) ? "0" : cartinFragment.getNumberDialog().getNumber_input(), "", "")
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doFinally(new Action() {
                                                @Override
                                                public void run() throws Exception {

                                                    cartinFragment.hideLoading();
                                                }
                                            })
                                            .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(appComponent.rxErrorHandler()) {
                                                @Override
                                                public void onNext(List<CartinItem> list) {
                                                    cartinFragment.setSum(list);
                                                    cartinAdapter.update(list);
                                                }
                                            });
                                }
                            }
                        }
                    });

            }
        });

        

    }

    @Override
    public void setOnItemClickListener(OnViewClickListener listener) {

        super.setOnItemClickListener(listener);
    }
}
