package com.codetech.apson.shop.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.api.Api;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;

import butterknife.BindView;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.http.imageloader.glide.GlideArt;

public class PayCartinListItemHolder extends BaseHolder<CartinItem> {
    @BindView(R.id.goods_name)
    TextView goods_name;
    @BindView(R.id.goods_img)
    ImageView goods_img;
    @BindView(R.id.goods_price)
    TextView price;
    @BindView(R.id.number)
    TextView number;

    public PayCartinListItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(CartinItem data, int position) {
        goods_name.setText(data.getGoods_name());
        GlideArt.with(itemView).load(Api.APP_DOMAIN+data.getGoods_img()).into(goods_img);
        price.setText("价格:"+data.getGoods_price());
        number.setText("数量："+data.getGoods_number());
    }

}
