package com.codetech.apson.shop.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;

import butterknife.BindView;
import me.jessyan.art.base.BaseHolder;

public class LeftClassifyItemHolder extends BaseHolder<Classify_Goods> {
    @BindView(R.id.classify_name)
    TextView classify_name;
    public LeftClassifyItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Classify_Goods data, int position) {
        classify_name.setText(data.getDad());
    }
}
