package com.codetech.apson.shop.mvp.ui.adapter;

import android.view.View;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.app.utils.MyUtils;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;
import com.codetech.apson.shop.mvp.ui.fragment.CartinFragment;
import com.codetech.apson.shop.mvp.ui.holder.CartinItemHolder;
import com.squareup.haha.perflib.Main;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.http.imageloader.glide.GlideArt;

public class CartinAdapter extends DefaultAdapter<CartinItem> {
    MainPresenter mainPresenter;
    CartinFragment cartinFragment;
    public CartinAdapter(List<CartinItem> infos, MainPresenter mainPresenter, CartinFragment cartinFragment) {
        super(infos);
        this.mainPresenter = mainPresenter;
        this.cartinFragment = cartinFragment;
    }

    @Override
    public BaseHolder<CartinItem> getHolder(View v, int viewType) {
//
//        CartinItemHolder cartinItemHolder = new CartinItemHolder(v);
//        cartinItemHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {
//            @Override
//            public void onViewClick(View view, int position) {
//                if(view.getId() == R.id.cartin_select){
//
//                }
//
//            }
//        });
        return new CartinItemHolder(v,mainPresenter,cartinFragment,this);
    }

    public void update(List<CartinItem> list){

        mInfos = list;
        notifyDataSetChanged();
    }
    public List<CartinItem> getList(List<CartinItem> list){
        return list;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.cartin_recycle_list;
    }
}
