package com.hellom.memory.album;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellom.memory.CustomScrollSpeedLinearLayoutManager;
import com.hellom.memory.R;

public class AlbumListSortAdapter extends BaseQuickAdapter<AlbumItemBean, BaseViewHolder> {
    public AlbumListSortAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumItemBean item) {
        helper.setText(R.id.album_type, item.getAlbumTypeTitle());

        RecyclerView albumList = helper.getView(R.id.album_list);
        albumList.setLayoutManager(new CustomScrollSpeedLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        AlbumListAdapter albumListAdapter = new AlbumListAdapter(R.layout.layout_album_list_album_item);
        albumList.setAdapter(albumListAdapter);
        albumListAdapter.setNewData(item.getAlbumBeans());
    }
}
