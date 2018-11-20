package com.codetech.apson.shop.mvp.ui.activity;

import android.content.Context;
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

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

import static me.jessyan.art.utils.Preconditions.checkNotNull;

import com.codetech.apson.shop.mvp.presenter.RegisterPresenter;

import com.codetech.apson.shop.R;

import java.util.Timer;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IView {

    @BindView(R.id.register)
    Button register;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.user_pwd)
    EditText user_pwd;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if(edit.contains("\n") || edit.contains("\t")){
                    user_name.setText(edit.replace("\n","").replace("\t",""));
                    user_pwd.requestFocus();
                    user_pwd.setSelection(user_pwd.getText().toString().length());
                }
            }
        });
        user_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if (edit.contains("\n") || edit.contains("\t")) {
                    user_name.setText(edit.replace("\n", "").replace("\t", ""));
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (im == null || getCurrentFocus() == null) return;//空判断
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
            register.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (im == null || getCurrentFocus() == null) return;//空判断
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Message message =Message.obtain(RegisterActivity.this);
                    Bundle bundle =new Bundle();
                    bundle.putString("username",user_name.getText().toString());
                    bundle.putString("userpwd",user_pwd.getText().toString());
                    message.setData(bundle);
                    mPresenter.register(message);
                }
            });
    }

    @Override
    @Nullable
    public RegisterPresenter obtainPresenter() {
        return new RegisterPresenter(ArtUtils.obtainAppComponentFromContext(this));
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
                ArtUtils.snackbarText("注册成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RegisterActivity.this.finish();
                    }
                },1500);

                break;
            case 2:
                ArtUtils.snackbarText("注册失败,已经存在用户");
//                this.finish();
                break;
        }
    }
}
