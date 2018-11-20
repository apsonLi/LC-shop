package com.codetech.apson.shop.app.utils;

import android.view.View;

public  abstract class SingleClick implements View.OnClickListener {
    public static long lastTime;

    public abstract void singleClick(View v);

    @Override
    public void onClick(View v) {
        if (onDoubClick()) {
            return;
        }
        singleClick(v);
    }

    public boolean onDoubClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time > 1000) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}
