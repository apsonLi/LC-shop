package com.codetech.apson.shop.mvp.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.codetech.apson.shop.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CenterDialog extends Dialog implements View.OnClickListener{
    @BindView(R.id.dialog_sure)
    TextView  sure;
    @BindView(R.id.dialog_cancel)
    TextView cancle;
    @BindView(R.id.dialog_text)
    TextView dialog_text;
    private Context context;
    private View customView;
    protected  OnCenterItemClickListener centerItemClickListener = null;
    public CenterDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

   public View getCustomView(){
      return customView;
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.centerdialog);
        ButterKnife.bind(this);
        sure.setOnClickListener(this);
        cancle.setOnClickListener(this);
        dialog_text.setOnClickListener(this);

    }

    public CenterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        LayoutInflater inflater= LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.centerdialog, null);
    }

    protected CenterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.centerItemClickListener = listener;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(CenterDialog dialog, View view);

    }

    @Override
    public void onClick(View view) {

        if(centerItemClickListener != null){
            this.dismiss();
            centerItemClickListener.OnCenterItemClick(this,view);
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
