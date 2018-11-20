package com.codetech.apson.shop.mvp.ui.adapter;

import android.view.View;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;
import com.codetech.apson.shop.mvp.ui.holder.RightClassifyItemHolder;
import com.codetech.apson.shop.mvp.ui.holder.RightClassifyItemOtherHolder;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;

public class RightClassifyAdapter extends DefaultAdapter<Classify_Goods.ChildBean> {
    public RightClassifyAdapter(List<Classify_Goods.ChildBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Classify_Goods.ChildBean> getHolder(View v, int viewType) {
        if(viewType == 0)
        return new RightClassifyItemHolder(v);
        return new RightClassifyItemOtherHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if(position %2 != 0)
            return 0;
        return 1;
    }

    @Override
    public int getLayoutId(int viewType) {
        if(viewType == 0)
        return R.layout.right_classify;

        return R.layout.right_classify_other;
    }

    public void update(List<Classify_Goods.ChildBean> list){
        mInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }
}
