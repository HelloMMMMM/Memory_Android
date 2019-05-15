package com.hellom.memory.album;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellom.mediastore.util.mediastore.bean.AlbumBean;
import com.hellom.memory.R;
import com.hellom.memory.util.GlideUtil;

public class AlbumListAdapter extends BaseQuickAdapter<AlbumBean, BaseViewHolder> {
    public AlbumListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumBean item) {
        helper.setText(R.id.album_name, item.getAlbumName());
        helper.setText(R.id.album_photo_total, mContext.getString(R.string.fg_album_photo_total, item.getImageTotal()));
        ImageView albumPreview = helper.getView(R.id.album_preview);
        GlideUtil.loadCenterCorpImage(mContext, item.getAlbumPreviewPath(), albumPreview);
    }
}
