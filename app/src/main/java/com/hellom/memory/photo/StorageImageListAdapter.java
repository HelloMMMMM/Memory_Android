package com.hellom.memory.photo;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellom.memory.R;
import com.hellom.memory.photo.util.StorageImage.StorageImageContentItemBean;

import java.util.List;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class StorageImageListAdapter extends BaseMultiItemQuickAdapter<StorageImageItemBean, BaseViewHolder> {

    public StorageImageListAdapter(List<StorageImageItemBean> data) {
        super(data);
        addItemType(StorageImageItemBean.ITEM_TYPE_DATE, R.layout.layout_storage_image_list_date_item);
        addItemType(StorageImageItemBean.ITEM_TYPE_CONTENT, R.layout.layout_storage_image_list_image_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorageImageItemBean item) {
        int itemType = item.getItemType();
        switch (itemType) {
            case StorageImageItemBean.ITEM_TYPE_DATE:
                StorageImageDateItemBean storageImageDateItemBean = (StorageImageDateItemBean) item;
                helper.setText(R.id.tv_storage_image_date, storageImageDateItemBean.getDate());
                break;
            case StorageImageItemBean.ITEM_TYPE_CONTENT:
                ImageView storageImage = helper.getView(R.id.iv_storage_image);
                StorageImageContentItemBean storageImageContentItemBean = (StorageImageContentItemBean) item;
                Glide.with(mContext).load(storageImageContentItemBean.getPath()).into(storageImage);
                break;
            default:
                break;
        }
    }
}
