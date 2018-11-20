package com.codetech.apson.shop.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.api.Api;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.model.entity.User;

import butterknife.BindView;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.http.imageloader.ImageLoader;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.http.imageloader.glide.ImageConfigImpl;
import me.jessyan.art.utils.ArtUtils;

public class GoodsItemHolder extends BaseHolder<Goods> {
    @BindView(R.id.iv_avatar)
     ImageView mAvatar;
    @BindView(R.id.goods_name)
     TextView name;

    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.sold)
    TextView sold;
    public GoodsItemHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void setData(Goods data, int position) {
        name.setText(data.getGoods_name());
        price.setText("¥"+data.getShop_price());
        sold.setText("销量:"+data.getSold());
        GlideArt.with(itemView).load(Api.APP_DOMAIN+data.getGoods_img()).placeholder(R.mipmap.art_logo).error(R.drawable.error).into(mAvatar);
//        Glide.with(itemView).load(Api.APP_DOMAIN+data.getGoods_img()).into(mAvatar);

    }


}
