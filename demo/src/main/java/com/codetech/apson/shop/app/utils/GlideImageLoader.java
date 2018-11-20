package com.codetech.apson.shop.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.codetech.apson.shop.R;
import com.youth.banner.loader.ImageLoader;

import me.jessyan.art.http.imageloader.glide.GlideArt;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideArt.with(context).load(path).error(R.drawable.f1).into(imageView);
    }
}
