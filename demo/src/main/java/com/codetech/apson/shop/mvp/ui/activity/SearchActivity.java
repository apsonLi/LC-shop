package com.codetech.apson.shop.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.presenter.SearchPresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.adapter.GoodsAdapter;

import java.util.ArrayList;


public class SearchActivity extends BaseActivity<SearchPresenter> implements IView {

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.search_edit)
    EditText search;
    @BindView(R.id.search)
    Button search_btn;
    @BindView(R.id.search_list)
    RecyclerView search_list;
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ArtUtils.statuInScreen(this);
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public float getStatusBarHeight() {
        float result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimension(resourceId);
        }
        return result;
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        appBarLayout.setPadding(0,(int)getStatusBarHeight(),0,0);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str=editable.toString();
                if (str.indexOf("\r")>=0 || str.indexOf("\n")>=0){//发现输入回车符或换行符
                    search.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                    search.setCursorVisible(false);//将软键盘隐藏
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(im == null || getCurrentFocus() == null) return;//空判断
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mPresenter.getSearchResult(Message.obtain(SearchActivity.this,search.getText().toString()));

                }
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setCursorVisible(false);//将软键盘隐藏
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(im == null || getCurrentFocus() == null) return;//空判断
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                mPresenter.getSearchResult(Message.obtain(SearchActivity.this,search.getText().toString()));
            }
        });
    }

    @Override
    @Nullable
    public SearchPresenter obtainPresenter() {
        return new SearchPresenter(ArtUtils.obtainAppComponentFromContext(this));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArtUtils.snackbarText(message);
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        checkNotNull(message);
        switch (message.what) {
            case 0:
                break;
            case 1:
                ArrayList<Goods> list = message.getData().getParcelableArrayList("result");
                GoodsAdapter goodsAdapter =new GoodsAdapter(list);
                goodsAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<Goods>() {
                    @Override
                    public void onItemClick(View view, int viewType, Goods data, int position) {
                        Intent intent = new Intent(SearchActivity.this,GoodsInfoActivity.class);
                        intent.putExtra("goodsid",data.getGoods_id());
                        startActivity(intent);
                    }
                });
                ArtUtils.configRecyclerView(search_list, new GridLayoutManager(SearchActivity.this, 2));
                search_list.setAdapter(goodsAdapter);
                break;
        }
    }
}
