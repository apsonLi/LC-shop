package com.codetech.apson.shop.mvp.ui.adapter;

import android.view.View;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;
import com.codetech.apson.shop.mvp.ui.holder.LeftClassifyItemHolder;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;

public class LeftClassifyAdapter extends DefaultAdapter<Classify_Goods> {


    public LeftClassifyAdapter(List<Classify_Goods> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Classify_Goods> getHolder(View v, int viewType) {

        return new LeftClassifyItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {


        return R.layout.left_classify;
    }


    @Override
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }
}
