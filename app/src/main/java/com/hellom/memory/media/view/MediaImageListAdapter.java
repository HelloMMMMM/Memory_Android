package com.hellom.memory.media.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellom.memory.R;
import com.hellom.memory.media.model.ContentItemBean;
import com.hellom.memory.media.model.DateItemBean;
import com.hellom.memory.media.model.ItemBean;

import java.util.List;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class MediaImageListAdapter extends BaseMultiItemQuickAdapter<ItemBean, BaseViewHolder> {

    MediaImageListAdapter(List<ItemBean> data) {
        super(data);
        addItemType(ItemBean.ITEM_TYPE_DATE, R.layout.layout_media_image_list_date_item);
        addItemType(ItemBean.ITEM_TYPE_CONTENT, R.layout.layout_media_image_list_image_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemBean item) {
        int itemType = item.getItemType();
        switch (itemType) {
            case ItemBean.ITEM_TYPE_DATE:
                DateItemBean storageImageDateItemBean = (DateItemBean) item;
                helper.setText(R.id.tv_media_image_date, storageImageDateItemBean.getDate());
                break;
            case ItemBean.ITEM_TYPE_CONTENT:
                ImageView storageImage = helper.getView(R.id.iv_media_image);
                ContentItemBean storageImageContentItemBean = (ContentItemBean) item;
                Glide.with(mContext).load(storageImageContentItemBean.getPath()).into(storageImage);
                helper.addOnClickListener(R.id.iv_media_image);
                break;
            default:
                break;
        }
    }
}
