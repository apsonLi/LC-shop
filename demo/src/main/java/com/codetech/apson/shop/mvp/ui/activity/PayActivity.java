package com.codetech.apson.shop.mvp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.app.utils.MyUtils;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.presenter.PayPresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.adapter.CartinAdapter;
import com.codetech.apson.shop.mvp.ui.adapter.PayCartinAdapter;
import com.codetech.apson.shop.mvp.ui.view.FullScreenLayout;

import java.util.List;


public class PayActivity extends BaseActivity<PayPresenter> implements IView {

    @BindView(R.id.name_text)
    TextView name;
    @BindView(R.id.address_text)
    TextView address;
    @BindView( R.id.change_address)
    Button changeaddress;
    @BindView(R.id.cartin)
    RecyclerView cartin;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.pay)
    Button pay;
    @BindView(R.id.loading)
    FullScreenLayout loading;
    @BindView(R.id.loading_view)
    ImageView loadingview;
    @BindView(R.id.head)
    RelativeLayout head;
    private SharedPreferences sp;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ArtUtils.statuInScreen(this);
        return R.layout.activity_pay; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        head.setPadding(0,(int)getStatusBarHeight(),0,0);
        if(getIntent().getExtras() == null) return;
        List<CartinItem> cartinItems = getIntent().getExtras().getParcelableArrayList("cartin_ready");
        sp = getSharedPreferences("userinfo",0);
        Address address_default = getIntent().getExtras().getParcelable("default_address");
        if(address_default == null){
           name.setText("");
           address.setText("");
        }
       else {
            name.setText(address_default.getConsignee());
            address.setText(String.format(getResources().getString(R.string.detailed_address),address_default.getProvince_addr(),address_default.getCity_addr(),address_default.getDistrict_addr(),address_default.getStreet()));
        }
        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(PayActivity.this,AddressActivity.class);
                startActivityForResult(intent,1);
            }
        });
        sum.setText(String.format(getResources().getString(R.string.sum),MyUtils.sumCartin(cartinItems)));
        PayCartinAdapter payCartinAdapter =new PayCartinAdapter(cartinItems);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ArtUtils.configRecyclerView(cartin,linearLayoutManager);
        cartin.setAdapter(payCartinAdapter);
        setResult(1);
        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this,AddressActivity.class);
                startActivityForResult(intent,1);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                mPresenter.pay(Message.obtain(PayActivity.this,new Object[]{sp.getString("userID",null), sp.getString("userKEY",null)}));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200 && data!=null && data.getExtras()!=null){
          mPresenter.getrealAddresss(Message.obtain(PayActivity.this,new Object[]{sp.getString("userID",null),sp.getString("userKEY",null)}));

        }

    }

    @Override
    @Nullable
    public PayPresenter obtainPresenter() {
        return new PayPresenter(ArtUtils.obtainAppComponentFromContext(this));
    }

    @Override
    public void showLoading() {
        GlideArt.with(this).load(R.drawable.loading).into(loadingview);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
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

                setResult(1);
                break;
            case 1:
                ArtUtils.snackbarText("支付成功");
                setResult(2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PayActivity.this.finish();
                    }
                },1000);

                break;
            case 3:
                Address address_default = message.getData().getParcelable("result");
                name.setText(address_default.getConsignee());
                address.setText(String.format(getResources().getString(R.string.detailed_address),address_default.getProvince_addr(),address_default.getCity_addr(),address_default.getDistrict_addr(),address_default.getStreet()));
        }
    }
}
