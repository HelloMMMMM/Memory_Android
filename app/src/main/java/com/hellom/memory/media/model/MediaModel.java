package com.hellom.memory.media.model;

import android.content.Context;

import com.hellom.mediastore.util.mediastore.ImageBean;
import com.hellom.mediastore.util.mediastore.ImageDateSortBean;
import com.hellom.mediastore.util.mediastore.MediaStoreUtil;

import java.util.ArrayList;
import java.util.List;


public class MediaModel {

    private List<String> sourceData;

    public List<String> getSourceData() {
        return sourceData;
    }

    public List<ItemBean> getDateSortItems(Context context) {
        List<ImageDateSortBean> imageDateSortBeans = getDateSortImages(context);
        convertSourceData(imageDateSortBeans);
        return convertDateSortData(imageDateSortBeans);
    }

    public List<ItemBean> getItems(Context context) {
        return convertData(getImages(context));
    }

    private List<ImageDateSortBean> getDateSortImages(Context context) {
        return MediaStoreUtil.getDateSortImagesFromStorage(context, "/storage/emulated/0/DCIM/Camera/", true);
        //return MediaStoreUtil.getDateSortImagesFromStorage(context, null, true);
    }

    private List<ImageBean> getImages(Context context) {
        return MediaStoreUtil.getImagesFromStorage(context, "/storage/emulated/0/DCIM/Camera/", true);
        //return MediaStoreUtil.getImagesFromStorage(context, null, true);
    }

    private List<ItemBean> convertDateSortData(List<ImageDateSortBean> imageDateSortBeans) {
        List<ItemBean> itemBeans = new ArrayList<>();
        for (ImageDateSortBean imageDateSortBean : imageDateSortBeans) {
            DateItemBean storageImageDateItemBean = new DateItemBean();
            storageImageDateItemBean.setDate(imageDateSortBean.getDate());
            itemBeans.add(storageImageDateItemBean);

            List<ImageBean> imageBeans = imageDateSortBean.getImageBeans();
            for (ImageBean imageBean : imageBeans) {
                ContentItemBean storageImageContentItemBean = new ContentItemBean();
                storageImageContentItemBean.setPath(imageBean.getPath());
                itemBeans.add(storageImageContentItemBean);
            }
        }
        return itemBeans;
    }

    private List<ItemBean> convertData(List<ImageBean> imageBeans) {
        List<ItemBean> itemBeans = new ArrayList<>();
        for (ImageBean imageBean : imageBeans) {
            ContentItemBean storageImageContentItemBean = new ContentItemBean();
            storageImageContentItemBean.setPath(imageBean.getPath());
            itemBeans.add(storageImageContentItemBean);
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
