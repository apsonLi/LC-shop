package com.codetech.apson.shop.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.presenter.AddressChangePresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.view.dialog.AddressDialog;


public class AddressChangeActivity extends BaseActivity<AddressChangePresenter> implements IView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.tel)
    EditText tel;
    @BindView(R.id.area_select)
    LinearLayout area_select;
    @BindView(R.id.area)
    TextView area_call;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.more_area)
    TextView area;
    @BindView(R.id.change_address)
    Button change;
//    private AddressRefreshBoardCastReceiver refreshBoardCastReceiver;
    private SharedPreferences sp;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ArtUtils.statuInScreen(this);
        return R.layout.activity_address_change; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        refreshBoardCastReceiver = new AddressRefreshBoardCastReceiver(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("top.codetech.apson.dialogdismiss");
//        this.registerReceiver(refreshBoardCastReceiver,intentFilter);

        head.setPadding(0,(int)getStatusBarHeight(),0,0);
           String addressid =  getIntent().getStringExtra("addressid");
           if(addressid == null){
               title.setText("添加收货地址");
           }
           else{
               title.setText("修改收货地址");
           }
           back.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                   AddressChangeActivity.this.finish();
               }
           });
           name.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void afterTextChanged(Editable editable) {

                   String str=editable.toString();
                   if (str.contains("\r") || str.contains("\n")){//发现输入回车符或换行符
                       name.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                       tel.requestFocus();//让editText2获取焦点
                       tel.setSelection(tel.getText().length());//若editText2有内容就将光标移动到文本末尾
                   }
               }
           });
           tel.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void afterTextChanged(Editable editable) {
                   String str=editable.toString();
                   if (str.contains("\r")|| str.contains("\n")){//发现输入回车符或换行符
                       tel.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                       tel.setCursorVisible(false);//将软键盘隐藏
                       InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                       if(im == null || getCurrentFocus() == null) return;//空判断
                       im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                   }
               }
           });
           area_select.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    AddressDialog addressDialog = new AddressDialog(AddressChangeActivity.this,R.style.dialog_custom);
                    addressDialog.setCanceledOnTouchOutside(true);

                    addressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            setAddress();
                        }
                    });
                    addressDialog.show();
               }
           });
           area.setHint("填写详细地址");
           area.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void afterTextChanged(Editable editable) {
                   String str=editable.toString();
                   if (str.contains("\r")|| str.contains("\n")){//发现输入回车符或换行符
                       area.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                       area.setCursorVisible(false);//将软键盘隐藏
                       InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                       if(im == null || getCurrentFocus() == null) return;//空判断
                       im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                   }
               }
           });
           change.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view) {
//                   mPresenter.postAddress(Message.obtain(AddressChangeActivity.this,new Object[]{}));
                   sp = AddressChangeActivity.this.getSharedPreferences("temaddress",0);
                   SharedPreferences userinfo = AddressChangeActivity.this.getSharedPreferences("userinfo",0);
                   if(sp.getString("provinceCode",null)==null|| sp.getString("provinceName",null)==null|| sp.getString("cityCode",null)==null
                           || sp.getString("cityName",null)==null|| sp.getString("districtCode",null)==null
                           || sp.getString("districtName",null)==null) return;

                   if(name.getText().toString().length()==0||tel.getText().toString().length()==0
                           ||area.getText().toString().length()==0){ArtUtils.snackbarText("信息缺少");return;}
//                   ArtUtils.snackbarText(String.format(getResources().getString(R.string.address), sp.getString("provinceName",null), sp.getString("cityName",null), sp.getString("districtName",null)));
                   mPresenter.postAddress(Message.obtain(AddressChangeActivity.this,new Object[]{
                           userinfo.getString("userID",null),userinfo.getString("userKEY",null),"0",userinfo.getString("userID",null)
                           ,name.getText().toString(),tel.getText().toString()
                           ,sp.getString("provinceName",null),sp.getString("cityName",null),sp.getString("districtName",null)
                           ,sp.getString("provinceCode",null),sp.getString("cityCode",null),sp.getString("districtCode",null)
                           ,area.getText().toString()

                   }));
               }
           });

    }


    public void setAddress( ){
        if(area_call!=null){
            sp = this.getSharedPreferences("temaddress",0);
            if(sp.getString("provinceCode",null)==null|| sp.getString("provinceName",null)==null|| sp.getString("cityCode",null)==null
                    || sp.getString("cityName",null)==null|| sp.getString("districtCode",null)==null
                    || sp.getString("districtName",null)==null) return;

            area_call.setText(String.format(getResources().getString(R.string.address), sp.getString("provinceName",null), sp.getString("cityName",null), sp.getString("districtName",null)));
        }
    }

    @Override
    @Nullable
    public AddressChangePresenter obtainPresenter() {
        return new AddressChangePresenter(ArtUtils.obtainAppComponentFromContext(this));
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
    protected void onDestroy() {
//        this.unregisterReceiver(refreshBoardCastReceiver);
        super.onDestroy();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        checkNotNull(message);
        switch (message.what) {
            case 0:
                break;
            case 1:
                ArtUtils.snackbarText("添加成功");
//                Intent intent =new Intent();
//                intent.setAction("top.codetech.apson.refreshaddress");
//                sendBroadcast(intent);
                setResult(RESULT_OK);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AddressChangeActivity.this.finish();
                    }
                },1000);
                break;
            case 2:
                ArtUtils.snackbarText("添加失败,缺少完整信息");
                break;
        }
    }
}
