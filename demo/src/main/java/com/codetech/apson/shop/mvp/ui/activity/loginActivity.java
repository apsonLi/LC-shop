package com.codetech.apson.shop.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.mvp.model.entity.ClientUser;
import com.codetech.apson.shop.mvp.presenter.loginPresenter;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.ui.view.FullScreenLayout;

import java.util.Objects;


public class loginActivity extends BaseActivity<loginPresenter> implements IView {
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.user_pwd)
    EditText user_pwd;
    @BindView(R.id.name)
    TextInputLayout name;
    @BindView(R.id.pwd)
    TextInputLayout pwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.lanjie)
    FullScreenLayout lanjie;
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }
    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_pwd.setCursorVisible(false);//将软键盘隐藏
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(im == null || getCurrentFocus() == null) return;//空判断
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String account = name.getEditText().getText().toString();
                String password = pwd.getEditText().getText().toString();

                name.setErrorEnabled(false);
                pwd.setErrorEnabled(false);

                //验证用户名和密码
                if(validateAccount(account)&&validatePassword(password)){
                    showLoading();

                    Object [] objects =new Object[]{account,password};
                    mPresenter.getClientUser(Message.obtain(loginActivity.this,objects));
                    Intent intent =new Intent();
                    intent.setAction("com.codetech.apson.refresh");
                    sendBroadcast(intent);
//                    Toast.makeText(TextInputLayoutActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                }


            }
        });
        user_name.addTextChangedListener(new MyWatcher());
        user_pwd.addTextChangedListener(new TextWatcher() {
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
                    user_name.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                    user_pwd.setCursorVisible(false);//将软键盘隐藏
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(im == null || getCurrentFocus() == null) return;//空判断
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }
        });
           user_name.setOnKeyListener(new View.OnKeyListener() {
               @Override
               public boolean onKey(View view, int i, KeyEvent keyEvent) {
                   if(i == KeyEvent.KEYCODE_ENTER){
                        user_pwd.requestFocus();//将焦点转移
                   }
                   return false;
                   }

           });
    }
    class MyWatcher implements TextWatcher{

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
                user_name.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                user_pwd.requestFocus();//让editText2获取焦点
                user_pwd.setSelection(user_pwd.getText().length());//若editText2有内容就将光标移动到文本末尾
            }

        }
    }
    /**
     * 验证用户名
     * @param account
     * @return
     */
    private boolean validateAccount(String account){
        if(account.equals("")){
            showError(name,"用户名不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        if (password .equals("")) {
            showError(pwd,"密码不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 18) {
            showError(pwd,"密码长度为6-18位");
            return false;
        }

        return true;
    }


    @Override
    @Nullable
    public loginPresenter obtainPresenter() {
        return new loginPresenter(ArtUtils.obtainAppComponentFromContext(this));
    }

    @Override
    public void showLoading() {
       loading.setVisibility(View.VISIBLE);
        GlideArt.with(this).load(R.drawable.loading).into(loading);
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
            case 1:
                lanjie.setVisibility(View.VISIBLE);
                ClientUser clientUser = message.getData().getParcelable("clientuser");
                SharedPreferences sp =getSharedPreferences("userinfo",0);
                if(clientUser==null){return;}
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("userID",clientUser.getUserID());
                editor.putString("userKEY",clientUser.getUserKEY());
                editor.apply();
                editor.commit();
                Intent intent = new Intent();
                intent.setAction("top.codetech.refreshfregment");
                sendBroadcast(intent);
                ArtUtils.snackbarText("登录成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginActivity.this.finish();
                    }
                },1500);
                break;
            case 2:
                lanjie.setVisibility(View.GONE);
                hideLoading();
                break;
        }
    }
}
