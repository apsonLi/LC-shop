package com.codetech.apson.shop.mvp.ui.adapter;

import android.view.View;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.ui.holder.PayCartinListItemHolder;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;

public class PayCartinAdapter extends DefaultAdapter<CartinItem> {
    public PayCartinAdapter(List<CartinItem> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<CartinItem> getHolder(View v, int viewType) {
        return new PayCartinListItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.pay_cartin_list;
    }
}
