package com.hellom.memory.photo.model;

import android.content.Context;

import com.hellom.mediastore.util.mediastore.bean.ImageBean;
import com.hellom.mediastore.util.mediastore.bean.ImageDateSortBean;
import com.hellom.mediastore.util.mediastore.MediaStoreUtil;

import java.util.ArrayList;
import java.util.List;

public class PhotoModel {

    private List<ItemBean> itemBeans;
    private List<ContentItemBean> sourceData;

    public List<ContentItemBean> getSourceData() {
        return sourceData;
    }

    public List<ItemBean> getDateSortItems(Context context) {
        return convertDateSortData(getDateSortImages(context));
    }

    public List<ItemBean> getItems(Context context) {
        return convertData(getImages(context));
    }

    private List<ImageDateSortBean> getDateSortImages(Context context) {
        return MediaStoreUtil.getDateSortImages(context, "/storage/emulated/0/DCIM", true);
    }

    private List<ImageBean> getImages(Context context) {
        return MediaStoreUtil.getNormalImages(context, "/storage/emulated/0/DCIM");
    }

    private List<ItemBean> convertDateSortData(List<ImageDateSortBean> imageDateSortBeans) {
        sourceData = new ArrayList<>();
        itemBeans = new ArrayList<>();
        for (ImageDateSortBean imageDateSortBean : imageDateSortBeans) {
            DateItemBean dateItemBean = new DateItemBean();
            dateItemBean.setDate(imageDateSortBean.getDate());
            itemBeans.add(dateItemBean);

            List<ImageBean> imageBeans = imageDateSortBean.getImageBeans();
            for (ImageBean imageBean : imageBeans) {
                itemBeans.add(wrapContentItemBean(imageBean));
            }
        }
        return itemBeans;
    }

    private List<ItemBean> convertData(List<ImageBean> imageBeans) {
        sourceData = new ArrayList<>();
        itemBeans = new ArrayList<>();
        for (ImageBean imageBean : imageBeans) {
            itemBeans.add(wrapContentItemBean(imageBean));
        }
        return itemBeans;
    }

    private ContentItemBean wrapContentItemBean(ImageBean imageBean) {
        ContentItemBean contentItemBean = new ContentItemBean();
        contentItemBean.setDate(imageBean.getDate());
        contentItemBean.setWidth(imageBean.getWidth());
        contentItemBean.setHeight(imageBean.getHeight());
        contentItemBean.setType(imageBean.getType());
        contentItemBean.setSize(imageBean.getSize());
        contentItemBean.setUri(imageBean.getPath());
        sourceData.add(contentItemBean);
        return contentItemBean;
    }

    public int deletePhoto(String uri) {
        sourceData.remove(getIndexInSourceData(uri));
        for (ItemBean itemBean : itemBeans) {
            if (itemBean instanceof ContentItemBean) {
                ContentItemBean tempContentItemBean = (ContentItemBean) itemBean;
                if (tempContentItemBean.getUri().equals(uri)) {
                    int listIndex = itemBeans.indexOf(itemBean);
                    itemBeans.remove(itemBean);
                    return listIndex;
                }
            }
        }
        return -1;
    }

    private int getIndexInSourceData(String uri) {
        for (int i = 0; i < sourceData.size(); i++) {
            ContentItemBean contentItemBean = sourceData.get(i);
            if (contentItemBean.getUri().equals(uri)) {
                return i;
            }
        }
        return -1;
    }

    public int getIndexInSourceData(ContentItemBean contentItemBean) {
        return sourceData.indexOf(contentItemBean);
    }
}
