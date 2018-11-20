package com.codetech.apson.shop.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class FullScreenLayout extends RelativeLayout {
    public FullScreenLayout(Context context) {
        super(context);
    }

    public FullScreenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("fullscreen","事件拦截");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("fullscreen","on touch event"+"事件id"+event.getAction());
        return true;
    }
}
