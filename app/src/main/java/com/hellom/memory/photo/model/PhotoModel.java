package com.hellom.memory.photo.model;

import android.content.Context;

import com.hellom.mediastore.util.mediastore.bean.ImageBean;
import com.hellom.mediastore.util.mediastore.bean.ImageDateSortBean;
import com.hellom.mediastore.util.mediastore.MediaStoreUtil;

import java.util.ArrayList;
import java.util.List;


public class PhotoModel {

    private List<String> sourceData;

    public List<String> getSourceData() {
        return sourceData;
    }

    public List<ItemBean> getDateSortItems(Context context) {
        List<ImageDateSortBean> imageDateSortBeans = getDateSortImages(context);
        //convertSourceData(imageDateSortBeans);
        return convertDateSortData(imageDateSortBeans);
    }

    public List<ItemBean> getItems(Context context) {
        return convertData(getImages(context));
    }

    private List<ImageDateSortBean> getDateSortImages(Context context) {
        return MediaStoreUtil.getDateSortImages(context, "/storage/emulated/0/DCIM", true);
        //return MediaStoreUtil.getDateSortImagesFromStorage(context, null, true);
    }

    private List<ImageBean> getImages(Context context) {
        return MediaStoreUtil.getImages(context, "/storage/emulated/0/DCIM", true);
        //return MediaStoreUtil.getImagesFromStorage(context, null, true);
    }

    private List<ItemBean> convertDateSortData(List<ImageDateSortBean> imageDateSortBeans) {
        sourceData = new ArrayList<>();
        List<ItemBean> itemBeans = new ArrayList<>();
        for (ImageDateSortBean imageDateSortBean : imageDateSortBeans) {
            DateItemBean dateItemBean = new DateItemBean();
            dateItemBean.setDate(imageDateSortBean.getDate());
            itemBeans.add(dateItemBean);

            List<ImageBean> imageBeans = imageDateSortBean.getImageBeans();
            for (ImageBean imageBean : imageBeans) {
                ContentItemBean contentItemBean = new ContentItemBean();
                contentItemBean.setIndex(sourceData.size());
                contentItemBean.setUri(imageBean.getPath());
                itemBeans.add(contentItemBean);
                sourceData.add(imageBean.getPath());
            }
        }
        return itemBeans;
    }

    private List<ItemBean> convertData(List<ImageBean> imageBeans) {
        sourceData = new ArrayList<>();
        List<ItemBean> itemBeans = new ArrayList<>();
        for (ImageBean imageBean : imageBeans) {
            ContentItemBean contentItemBean = new ContentItemBean();
            contentItemBean.setIndex(sourceData.size());
            contentItemBean.setUri(imageBean.getPath());
            itemBeans.add(contentItemBean);
            sourceData.add(imageBean.getPath());
        }
        return itemBeans;
    }

    private void convertSourceData(List<ImageDateSortBean> imageDateSortBeans) {
        sourceData = new ArrayList<>();
        for (ImageDateSortBean imageDateSortBean : imageDateSortBeans) {
            List<ImageBean> imageBeans = imageDateSortBean.getImageBeans();
            for (ImageBean imageBean : imageBeans) {
                sourceData.add(imageBean.getPath());
            }
        }
    }
}
