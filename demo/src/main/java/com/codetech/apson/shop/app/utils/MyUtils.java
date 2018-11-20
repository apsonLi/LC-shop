package com.codetech.apson.shop.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.codetech.apson.shop.mvp.model.entity.Area;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.model.entity.MyArea;

import java.util.ArrayList;
import java.util.List;

public class MyUtils {

    public static Boolean isCartrinSelectAll(List<CartinItem> list, View view){
        view.setTag(1);
        for (CartinItem cartinItem :list){
            if(!cartinItem.getStatus() .equals("1")){view.setTag(0);return false;}

        }
        return true;
    }
    public static float sumCartin(List<CartinItem> list) {
        if(list.size() ==0) return 0;
        float result = 0;
        try {
            for (CartinItem cartinItem : list) {
                if(cartinItem.getStatus().equals("1"))
                    result += Float.parseFloat(cartinItem.getGoods_price()) * Float.parseFloat(cartinItem.getGoods_number());
            }
        }
        catch (Exception e){
            return 0;
        }
        return result;
    }
    public static void tip(List<CartinItem> data,View view,View view1){
        if (data.size() == 0) {
            view.setVisibility(View.VISIBLE);
            view1.setVisibility(View.GONE);
        }
        else {view.setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);}
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static  List<MyArea> getArea(Area area) {
        if(area == null) return null;
        List<MyArea> myAreas = new ArrayList<>();
        for(Area.ResBean bean:area.getRes()){
                MyArea myArea = new MyArea(bean.getName(),bean.getCode(),"province");
                myAreas.add(myArea);
                    }
        return myAreas;
    }

}
