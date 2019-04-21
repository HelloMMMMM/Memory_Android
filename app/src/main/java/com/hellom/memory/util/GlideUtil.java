package com.hellom.memory.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {

    private GlideUtil() {
        throw new UnsupportedOperationException("u cannot init me!");
    }

    public static void loadFitCenterImage(Context context, Object src, ImageView imageView) {
        Glide.with(context).load(src).fitCenter().into(imageView);
    }

    public static void loadCenterCorpImage(Context context, Object src, ImageView imageView) {
        Glide.with(context).load(src).centerCrop().into(imageView);
    }
}
