package com.hellom.memory.photo.view;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellom.memory.R;
import com.hellom.memory.photo.model.ContentItemBean;
import com.hellom.memory.photo.model.DateItemBean;
import com.hellom.memory.photo.model.ItemBean;
import com.hellom.memory.util.GlideUtil;

import java.util.List;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class PhotoListAdapter extends BaseMultiItemQuickAdapter<ItemBean, BaseViewHolder> {

    PhotoListAdapter(List<ItemBean> data) {
        super(data);
        addItemType(ItemBean.ITEM_TYPE_DATE, R.layout.layout_photo_list_date_item);
        addItemType(ItemBean.ITEM_TYPE_CONTENT, R.layout.layout_photo_list_photo_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemBean item) {
        int itemType = item.getItemType();
        switch (itemType) {
            case ItemBean.ITEM_TYPE_DATE:
                DateItemBean dateItemBean = (DateItemBean) item;
                helper.setText(R.id.tv_media_image_date, dateItemBean.getDate());
                break;
            case ItemBean.ITEM_TYPE_CONTENT:
                ImageView image = helper.getView(R.id.iv_photo);
                ContentItemBean contentItemBean = (ContentItemBean) item;
                GlideUtil.loadCenterCorpImage(mContext, contentItemBean.getPath(), image);
                helper.addOnClickListener(R.id.iv_photo);
                break;
            default:
                break;
        }
    }
}
