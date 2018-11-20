package com.codetech.apson.shop.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.ui.holder.AddressItemHolder;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;

public class AddressAdapter extends DefaultAdapter<Address> {

    public AddressAdapter(List<Address> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Address> getHolder(View v, int viewType) {
        return new AddressItemHolder(v,this);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.address_list_item;
    }
    public void update(List<Address> lsit){

        mInfos = lsit;
        notifyDataSetChanged();

    }


}
