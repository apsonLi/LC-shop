package com.codetech.apson.shop.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.FirstFragment;
import com.codetech.apson.shop.app.utils.SingleClick;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.adapter.MyPageAdapter;
import com.codetech.apson.shop.mvp.ui.fragment.CartinFragment;
import com.codetech.apson.shop.mvp.ui.fragment.ClassifyFragment;
import com.codetech.apson.shop.mvp.ui.fragment.IndexFragment;
import com.codetech.apson.shop.mvp.ui.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<MainPresenter> implements IView ,IndexFragment.OnFragmentInteractionListener,ClassifyFragment.OnFragmentInteractionListener,CartinFragment.OnFragmentInteractionListener,MeFragment.OnFragmentInteractionListener{


    @BindView(R.id.tab_bottom)
    TabLayout tb_l;
    @BindView(R.id.main_vp)
    ViewPager content;
    @BindView(R.id.bootpage)
    ImageView bootpage;

    private String[] titles = new String[]{"首页", "分类", "购物车", "我的"};

    private List<Fragment> fragmentList;
    private RefreshFregment refreshFregment;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {

        ArtUtils.statuInScreen(MainActivity.this);
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initTab();
        initViewPage();
        refreshFregment = new RefreshFregment();
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("top.codetech.refreshfregment");
        registerReceiver(refreshFregment,intentFilter);
    }
    public void initTab(){
//        tb_l.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        tb_l.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tb_l.setTabMode(TabLayout.MODE_FIXED);
        tb_l.setSelectedTabIndicatorHeight(0);


    }
    private long firstTime=0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    public class RefreshFregment extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            initViewPage();
            MainActivity.this.unregisterReceiver(this);
        }
    }
    public void initViewPage(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new IndexFragment());
        fragmentList.add(new ClassifyFragment());
        fragmentList.add(new CartinFragment());
        fragmentList.add(new MeFragment());
        ContentAdapter contentAdapter = new ContentAdapter(getSupportFragmentManager(),fragmentList,this);
        content.setAdapter(contentAdapter);
        tb_l.setupWithViewPager(content);
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tb_l.getTabAt(i);
            if (tab != null) {
                //注意！！！这里就是添加我们自定义的布局
                tab.setCustomView(contentAdapter.getCustomView(i));
                //这里是初始化时，默认item0被选中，setSelected（true）是为了给图片和文字设置选中效果
                if (i == 0) {
                    ((ImageView) tab.getCustomView().findViewById(R.id.tab_iv)).setSelected(true);
                    ((TextView) tab.getCustomView().findViewById(R.id.tab_tv)).setSelected(true);
                }
                View tabview = (View) tab.getCustomView().getParent();
                tabview.setTag(i);
               tabview.setOnClickListener(mlistener);
            }
        }
        content.setOffscreenPageLimit(4);

    }
    long pre_time ;
    public View.OnClickListener mlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            long time = System.currentTimeMillis() - pre_time;
            if(time>1000){
                content.setCurrentItem((int)view.getTag());
            }
        }
    };



    @Override
    @Nullable
    public MainPresenter obtainPresenter() {
        return new MainPresenter(ArtUtils.obtainAppComponentFromContext(this));
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
            case 3:

                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    protected void onDestroy() {
        this.unregisterReceiver(refreshFregment);
        super.onDestroy();

    }

    class ContentAdapter extends FragmentPagerAdapter{
        List <Fragment> list;
        Context context;
        public ContentAdapter(FragmentManager fm, List<Fragment> list, Context context) {
            super(fm);
            this.context = context;
            this.list =list;
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list.size();
        }

//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }

        public View getCustomView(int position){
            View view= LayoutInflater.from(context).inflate(R.layout.tab_item,null);
            ImageView iv= (ImageView) view.findViewById(R.id.tab_iv);
            TextView tv= (TextView) view.findViewById(R.id.tab_tv);
            switch (position){
                case 0:
                    iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_selector));
                    tv.setText(titles[0]);
                    tv.setTextColor(R.drawable.tv_selectot);
                    break;
                case 1:
                    iv.setImageDrawable(context.getResources().getDrawable(R.drawable.classify_selector));
                    tv.setText(titles[1]);
                    tv.setTextColor(R.drawable.tv_selectot);
                    break;
                case 2:
                    iv.setImageDrawable(context.getResources().getDrawable(R.drawable.cart_selector));
                    tv.setText(titles[2]);
                    tv.setTextColor(R.drawable.tv_selectot);
                    break;
                case 3:
                    iv.setImageDrawable(context.getResources().getDrawable(R.drawable.me_selector));
                    tv.setText(titles[3]);
                    tv.setTextColor(R.drawable.tv_selectot);
                    break;
            }
            return view;
        }
    }

}
