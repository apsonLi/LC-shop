package com.codetech.apson.shop.mvp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.mvp.model.api.Api;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.model.entity.GoodsInfo;
import com.codetech.apson.shop.mvp.presenter.GoodsInfoPresenter;

import com.codetech.apson.shop.R;


public class GoodsInfoActivity extends BaseActivity<GoodsInfoPresenter> implements IView {

    @BindView(R.id.goods_info_img)
    ImageView goods_img;
    @BindView(R.id.goods_info_brief)
    TextView goods_info_brief;
    @BindView(R.id.goods_info_name)
    TextView goods_info_name;
    @BindView(R.id.goods_info_price)
    TextView goods_info_price;
    @BindView(R.id.goods_info_sold)
    TextView goods_info_sold;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.add_cartin)
    Button add_cartin;
    private String userID;
    private String userKEY;
    private String id;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ArtUtils.statuInScreen(this);
        return R.layout.activity_goods_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        id = getIntent().getStringExtra("goodsid");
        GlideArt.with(this).load(R.drawable.loading).into(loading);
        SharedPreferences sp = getSharedPreferences("userinfo",0);
        userID = sp.getString("userID",null);
        userKEY = sp.getString("userKEY",null);
        if(id !=null)
        mPresenter.getGoodsInfo(Message.obtain(this,new Object []{id}));
        showLoading();

    }

    @Override
    @Nullable
    public GoodsInfoPresenter obtainPresenter() {
        return new GoodsInfoPresenter(ArtUtils.obtainAppComponentFromContext(this));
    }

    @Override
    public void showLoading() {
            loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
            loading.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArtUtils.snackbarText(message);
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        checkNotNull(message);
        switch (message.what) {
            case 0:
                ArtUtils.snackbarText("添加成功");
                Intent intent =new Intent();
                intent.setAction("com.codetech.apson.refresh");
                sendBroadcast(intent);
                break;
            case 1:
                GoodsInfo goodsInfo = message.getData().getParcelable("goodsinfo");
                if(goodsInfo!=null){
                    GlideArt.with(this).load(Api.APP_DOMAIN+goodsInfo.getGoods_img()).into(goods_img);
                    goods_info_brief.setText(goodsInfo.getGoods_brief());
                    goods_info_name.setText(goodsInfo.getGoods_name());
                    goods_info_price.setText(String.format(getResources().getString(R.string.price),Float.parseFloat(goodsInfo.getShop_price())));
                    goods_info_sold.setText(String.format(getResources().getString(R.string.sold),Integer.parseInt(goodsInfo.getSold())));
                    add_cartin.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            if(userID == null || userKEY == null) ArtUtils.snackbarText("请先登录");
                                mPresenter.addcartin(Message.obtain(GoodsInfoActivity.this,new Object[]{userID,userKEY,id,goodsInfo.getGoods_name(),goodsInfo.getGoods_img(),goodsInfo.getShop_price()}));

                        }
                    });
                }

                break;
        }
    }
}
