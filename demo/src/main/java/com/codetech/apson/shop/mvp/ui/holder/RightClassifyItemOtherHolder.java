package com.codetech.apson.shop.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.api.Api;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;

import butterknife.BindView;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.http.imageloader.glide.GlideArt;

public class RightClassifyItemOtherHolder extends BaseHolder<Classify_Goods.ChildBean> {
    @BindView(R.id.classify_goods_name)
    TextView classifty_goods_name;
    @BindView(R.id.classify_iv_avatar)
    ImageView classify_iv_image;

    public RightClassifyItemOtherHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void setData(Classify_Goods.ChildBean data, int position) {
        classifty_goods_name.setText(data.getGoods_name());
        GlideArt.with(itemView).load(Api.APP_DOMAIN+data.getGoods_img()).placeholder(R.mipmap.art_logo).error(R.drawable.error).into(classify_iv_image);

    }
}
