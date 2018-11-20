package com.codetech.apson.shop.mvp.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Area;
import com.codetech.apson.shop.mvp.model.entity.MyArea;

import butterknife.BindView;
import me.jessyan.art.base.BaseHolder;

public class AddressDialogItemHolder extends BaseHolder<MyArea> {
    @BindView(R.id.address_item_name)
    TextView item;
    public AddressDialogItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(MyArea data, int position) {
        item.setText(data.getName());
    }

}
