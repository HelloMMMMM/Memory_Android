package com.hellom.memory.media.view;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellom.memory.R;
import com.hellom.memory.media.model.ContentItemBean;
import com.hellom.memory.media.model.DateItemBean;
import com.hellom.memory.media.model.ItemBean;
import com.hellom.memory.util.GlideUtil;

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
                DateItemBean dateItemBean = (DateItemBean) item;
                helper.setText(R.id.tv_media_image_date, dateItemBean.getDate());
                break;
            case ItemBean.ITEM_TYPE_CONTENT:
                ImageView image = helper.getView(R.id.iv_media_image);
                ContentItemBean contentItemBean = (ContentItemBean) item;
                GlideUtil.loadCenterCorpImage(mContext, contentItemBean.getPath(), image);
                helper.addOnClickListener(R.id.iv_media_image);
                break;
            default:
                break;
        }
    }
}
