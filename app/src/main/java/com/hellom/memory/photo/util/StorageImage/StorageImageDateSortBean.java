package com.hellom.memory.photo.util.StorageImage;

import java.util.List;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class StorageImageDateSortBean {
    private String date;

    private List<StorageImageBean> storageImageBeans;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StorageImageBean> getStorageImageBeans() {
        return storageImageBeans;
    }

    public void setStorageImageBeans(List<StorageImageBean> storageImageBeans) {
        this.storageImageBeans = storageImageBeans;
    }
}
