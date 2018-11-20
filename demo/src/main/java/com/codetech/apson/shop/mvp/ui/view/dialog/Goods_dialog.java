package com.codetech.apson.shop.mvp.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.codetech.apson.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Goods_dialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.dialog_sure)
    TextView sure;
    @BindView(R.id.dialog_cancel)
    TextView cancle;
    @BindView(R.id.number_input)
    EditText number_input;
    @BindView(R.id.number_input_layout)
    TextInputLayout inputLayout;
    protected GoodsOnClickListener goodsOnClickListener = null;
    public Goods_dialog(@NonNull Context context) {
        super(context);
    }

    public Goods_dialog(@NonNull Context context, int themeResId) {
        super(context, R.style.dialog_custom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goods_number);
        ButterKnife.bind(this);
        number_input.requestFocus();
        number_input.addTextChangedListener(new TextWatcher() {
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
                    number_input.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                    number_input.setCursorVisible(false);//将软键盘隐藏
                    InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(im == null || getCurrentFocus() == null) return;//空判断
                    im.hideSoftInputFromWindow(number_input.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                }
            }
        });
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
        number_input.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getWindow() !=null)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


}

    public void setNumber_input(String number){
        if(number_input!=null) {
            number_input.setText(number);
            number_input.setSelection(number_input.getText().length());
        }
    }
    public String getNumber_input(){
        if(number_input!=null)
        return number_input.getText().toString();
        return null;
    }

    protected Goods_dialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public interface GoodsOnClickListener{
        void goodsOnItemClick(View view);
    }

    public void setGoodsOnClickListener(GoodsOnClickListener goodsOnClickListener) {
        this.goodsOnClickListener = goodsOnClickListener;
    }

    @Override
    public void onClick(View view) {

        if(view.getId()!=R.id.number_input){
            if(number_input.getText().toString().length()>3){
                inputLayout.setError("数量不能大于200");
            }
            else if(number_input.getText().toString().equals("")){
                inputLayout.setError("数量不能为空");
            }
            else if(Integer.parseInt(number_input.getText().toString()) < 1){
                inputLayout.setError("不能小于1");
            }
            else if(Integer.parseInt(number_input.getText().toString()) > 200){
                inputLayout.setError("不能大于200");
            }

            else if(goodsOnClickListener !=null){
                this.dismiss();
                goodsOnClickListener.goodsOnItemClick(view);
            }
        }

    }



    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= getWindow().getWindowManager().getDefaultDisplay().getWidth()*4/5;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
}
