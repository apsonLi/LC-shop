package com.codetech.apson.shop.mvp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.FirstFragment;
import com.codetech.apson.shop.mvp.presenter.StartPagePresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.adapter.MyPageAdapter;
import com.codetech.apson.shop.mvp.ui.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;


public class StartPageActivity extends BaseActivity<StartPagePresenter> implements IView ,FirstFragment.OnFragmentInteractionListener ,SecondFragment.OnFragmentInteractionListener {

    @BindView(R.id.vp_welcome)
    ViewPager vp_welecom;
    @BindView(R.id.bootpage)
    ImageView bootpage;
    List<Fragment> fragmentList ;
    private MyPageAdapter adapter;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ArtUtils.statuInScreen(this);
        return R.layout.activity_start_page; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        Boolean first_run = sharedPreferences.getBoolean("First",true);
        bootpage.setVisibility(View.GONE);
        vp_welecom.setVisibility(View.GONE);
        if (first_run){
            vp_welecom.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("First",false);
//            editor.commit();
            editor.apply();
//            sharedPreferences.edit().putBoolean("First",false).commit();
//            Toast.makeText(this,"第一次",Toast.LENGTH_LONG).show();
            FirstFragment f= new FirstFragment();
            SecondFragment f1= new SecondFragment();
            fragmentList =new ArrayList<>();
            fragmentList.add(f);
            fragmentList.add(f1);

            adapter = new MyPageAdapter(getSupportFragmentManager(),fragmentList);
            vp_welecom.setAdapter(adapter);

        }
        else {
            bootpage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StartPageActivity.this,MainActivity.class);
                    startActivity(intent);
                    StartPageActivity.this.finish();
//            Toast.makeText(this,"不是第一次",Toast.LENGTH_LONG).show();
                }
            },3000);


        }



    }

    @Override
    @Nullable
    public StartPagePresenter obtainPresenter() {
        return new StartPagePresenter(ArtUtils.obtainAppComponentFromContext(this));
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
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
