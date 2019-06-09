package com.hellom.memory.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hellom.memory.R;

public class GlideUtil {

    private static RequestOptions defaultOptions;

    private static RequestOptions getDefaultOptions() {
        if (defaultOptions == null) {
            defaultOptions = new RequestOptions().placeholder(R.drawable.bg_glide_load_default_placeholder)
                    .error(R.drawable.bg_glide_load_default_placeholder)
                    .fallback(R.drawable.bg_glide_load_default_placeholder);
        }
        return defaultOptions;
    }

    private GlideUtil() {
        throw new UnsupportedOperationException("u cannot init me!");
    }

    public static void loadFitCenterImage(Context context, Object src, ImageView imageView) {
        Glide.with(context).load(src).apply(getDefaultOptions()).fitCenter().into(imageView);
    }

    public static void loadCenterCorpImage(Context context, Object src, ImageView imageView) {
        Glide.with(context).load(src).apply(getDefaultOptions()).centerCrop().into(imageView);
    }

    public static void loadFitCenterOriginImage(Context context, Object src, final ImageView imageView) {
        RequestOptions requestOptions = getDefaultOptions()
                .placeholder(R.drawable.bg_glide_load_origin_placeholder)
                .error(R.drawable.bg_glide_load_origin_placeholder)
                .fallback(R.drawable.bg_glide_load_origin_placeholder);
        Glide.with(context).load(src).apply(requestOptions).thumbnail(0.1f).fitCenter().override(Target.SIZE_ORIGINAL).into(imageView);
    }
}
