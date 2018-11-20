package com.codetech.apson.shop.mvp.ui.adapter;

import android.view.View;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Area;
import com.codetech.apson.shop.mvp.model.entity.MyArea;
import com.codetech.apson.shop.mvp.ui.holder.AddressDialogItemHolder;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;

public class DialogAddressListAdapter extends DefaultAdapter<MyArea> {


    public DialogAddressListAdapter(List<MyArea> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MyArea> getHolder(View v, int viewType) {
        return new AddressDialogItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.dialog_address_list;
    }
    public void update(List<MyArea> list){
        mInfos = list;
        notifyDataSetChanged();
    }
}
