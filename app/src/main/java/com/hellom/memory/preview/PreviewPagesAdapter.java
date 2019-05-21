package com.hellom.memory.preview;

import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.R;

public class PreviewPagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    PreviewPagesAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        SubsamplingScaleImageView page = helper.getView(R.id.preview_page);
        setLayoutParams(page.getLayoutParams(), helper.getAdapterPosition());
        resetPage(page);
        setPageImage(page, item);
    }

    private void setLayoutParams(ViewGroup.LayoutParams layoutParams, int position) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int space = (int) (displayMetrics.density * 8);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            if (position > 0 && position <= getItemCount() - 1) {
                marginLayoutParams.setMargins(space, 0, 0, 0);
                width += space;
            }
        }
        layoutParams.width = width;
        layoutParams.height = displayMetrics.heightPixels;
    }

    private void resetPage(SubsamplingScaleImageView page) {
        page.recycle();
        page.setDoubleTapZoomScale(1.0f);
        page.setDoubleTapZoomDuration(150);
    }

    private void setPageImage(SubsamplingScaleImageView page, String uri) {
        page.setImage(ImageSource.uri(uri));
    }
}
