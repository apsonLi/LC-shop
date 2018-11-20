package com.codetech.apson.shop.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.ui.holder.GoodsItemHolder;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.utils.ArtUtils;

public class GoodsAdapter extends DefaultAdapter<Goods>  {


    public GoodsAdapter(List<Goods> infos) {
        super(infos);
    }


    @Override
    public BaseHolder<Goods> getHolder(View v, int viewType) {
        return new GoodsItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycle_list;
    }


    @Override
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }
    public void update(List<Goods> list){
        mInfos = list;
        notifyDataSetChanged();
    }

}

