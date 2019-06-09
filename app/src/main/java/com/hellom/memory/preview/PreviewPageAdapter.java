package com.hellom.memory.preview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.photo.model.ContentItemBean;

import java.util.ArrayList;
import java.util.List;

public class PreviewPageAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
    private List<ContentItemBean> srcUris;

    private static final int DEFAULT_CACHE_VIEWS_TOTAL = 3;
    private int cacheSize = DEFAULT_CACHE_VIEWS_TOTAL;
    private List<View> cachePages;

    private OnItemClickListener onItemClickListener;

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    PreviewPageAdapter(Context context, List<ContentItemBean> srcUris) {
        this.context = context;
        this.srcUris = srcUris;
        initCachePages();
    }

    PreviewPageAdapter(Context context, List<ContentItemBean> srcUris, int pageOffsetLimit) {
        this.context = context;
        this.srcUris = srcUris;
        this.cacheSize = pageOffsetLimit * 2 + 1;
        initCachePages();
    }

    void setSrcUris(List<ContentItemBean> srcUris) {
        this.srcUris = srcUris;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return srcUris == null ? 0 : srcUris.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return initPage(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    private void initCachePages() {
        cachePages = new ArrayList<>(cacheSize);
        for (int i = 0; i < cacheSize; i++) {
            SubsamplingScaleImageView page = new SubsamplingScaleImageView(context);
            page.setDoubleTapZoomScale(1.2f);
            page.setDoubleTapZoomDuration(150);
            page.setOnClickListener(this);
            cachePages.add(page);
        }
    }

    private View initPage(ViewGroup container, int position) {
        SubsamplingScaleImageView page = (SubsamplingScaleImageView) cachePages.get(position % cacheSize);
        container.removeView(page);
        page.setImage(ImageSource.uri(srcUris.get(position).getUri()));
        container.addView(page);
        return page;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick();
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    ContentItemBean getCurrentItemData(int position) {
        return srcUris.get(position);
    }

    void deleteCurrentItemData(int position) {
        srcUris.remove(position);
        notifyDataSetChanged();
    }
}
