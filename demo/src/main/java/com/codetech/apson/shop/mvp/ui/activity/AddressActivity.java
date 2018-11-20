package com.codetech.apson.shop.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.presenter.AddressPresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.adapter.AddressAdapter;
import com.codetech.apson.shop.mvp.ui.fragment.CartinFragment;
import com.codetech.apson.shop.mvp.ui.view.dialog.CenterDialog;

import java.util.ArrayList;


public class AddressActivity extends BaseActivity<AddressPresenter> implements IView {

    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.address_list)
    RecyclerView address_list;
    @BindView(R.id.add_address)
    Button add_address;
    @BindView(R.id.loading)
    ImageView loading;
    private String userID;
    private AddressAdapter addressAdapter;
    private Bundle bundle = new Bundle();
    private String userKEY;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ArtUtils.statuInScreen(this);
        return R.layout.activity_address; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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


        GlideArt.with(this).load(R.drawable.loading).into(loading);
        head.setPadding(0,(int)getStatusBarHeight(),0,0);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.getrealAddresss(Message.obtain(AddressActivity.this,new Object[]{userID,userKEY})).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ErrorHandleSubscriber<ArrayList<Address>>(ArtUtils.obtainAppComponentFromContext(AddressActivity.this).rxErrorHandler()) {
                            @Override
                            public void onNext(ArrayList<Address> addresses) {

                                bundle.putParcelableArrayList("return_address_list",addresses);
                                Intent mIntent = new Intent();
                                mIntent.putExtras(bundle);
                                setResult(200, mIntent);
                                AddressActivity.this.finish();
                            }
                        });

            }
        });
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AddressActivity.this,AddressChangeActivity.class);
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });


        SharedPreferences sp = getSharedPreferences("userinfo",0);
        userID = sp.getString("userID",null);
        userKEY = sp.getString("userKEY",null);
        if(userID == null) throw  new RuntimeException("你还没有登录");
        showLoading();
        mPresenter.getAddress(Message.obtain(this, new Object[]{userID,userKEY}));
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                mPresenter.getrealAddresss(Message.obtain(AddressActivity.this,new Object[]{userID,userKEY})).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ErrorHandleSubscriber<ArrayList<Address>>(ArtUtils.obtainAppComponentFromContext(AddressActivity.this).rxErrorHandler()) {



                            @Override
                            public void onNext(ArrayList<Address> addresses) {

                                bundle.putParcelableArrayList("return_address_list",addresses);


                            }
                        });
                break;
        }
        Intent mIntent = new Intent();
        mIntent.putExtras(bundle);
        setResult(200, mIntent);
        return super.onKeyUp(keyCode, event);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    showLoading();
                    mPresenter.getrealAddresss(Message.obtain(AddressActivity.this,new Object[]{userID,userKEY})).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ErrorHandleSubscriber<ArrayList<Address>>(ArtUtils.obtainAppComponentFromContext(AddressActivity.this).rxErrorHandler()) {
                                @Override
                                public void onNext(ArrayList<Address> addresses) {
                                    addressAdapter.update(addresses);
                                    hideLoading();
                                }
                            });
                }

        }
    }


    @Override
    @Nullable
    public AddressPresenter obtainPresenter() {
        return new AddressPresenter(ArtUtils.obtainAppComponentFromContext(this));
    }

    @Override
    public void showLoading() {
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
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void handleMessage(@NonNull Message message) {
        checkNotNull(message);
        switch (message.what) {
            case 0:

                break;
            case 1:
                ArrayList<Address>  resutl_list = message.getData().getParcelableArrayList("addresslist");
                addressAdapter = new AddressAdapter(resutl_list);
                addressAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<Address>() {
                    @Override
                    public void onItemClick(View view, int viewType, Address data, int position) {
                        if(view.getTag()!=null)
                        Log.e("test",view.getId()+""+view.getTag());
                    }
                });
                addressAdapter.setOnItemLongClickListener(new DefaultAdapter.OnRecyclerViewItemLongClickListener<Address>() {
                    @Override
                    public void onItemLongClick(View view, int viewType, Address data, int position) {
                        CenterDialog centerDialog =new CenterDialog(AddressActivity.this,R.style.dialog_custom);
                        centerDialog.setCanceledOnTouchOutside(true);// 点击Dialog外部消失
                        View customView = centerDialog.getCustomView();
                        TextView textView = (TextView) customView.findViewById(R.id.dialog_text);
                        textView.setText("是否删除该地址");
//                        centerDialog.setDialog_text("是否删除该地址");
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if(view.getId() == R.id.dialog_sure){
                                    showLoading();
                                    mPresenter.getrealAddresss(Message.obtain(AddressActivity.this,new Object[]{userID,userKEY})).subscribeOn(Schedulers.io())
                                            .map(new Function<ArrayList<Address>, ArrayList<Address>>() {
                                                @Override
                                                public ArrayList<Address> apply(ArrayList<Address> addresses) {
                                                    mPresenter.delAddress(Message.obtain(AddressActivity.this,new Object[]{data.getAddress_id(),userID,userKEY}));
                                                    return addresses;
                                                }
                                            })
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new ErrorHandleSubscriber<ArrayList<Address>>(ArtUtils.obtainAppComponentFromContext(AddressActivity.this).rxErrorHandler()) {
                                                @Override
                                                public void onNext(ArrayList<Address> addresses) {
                                                    addressAdapter.update(addresses);//刷新被删除后的结果
                                                    hideLoading();
                                                }
                                            });

                                }
                            }
                        });
                        centerDialog.show();
                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                ArtUtils.configRecyclerView(address_list,linearLayoutManager);
                address_list.setAdapter(addressAdapter);

                break;
        }
    }
}
