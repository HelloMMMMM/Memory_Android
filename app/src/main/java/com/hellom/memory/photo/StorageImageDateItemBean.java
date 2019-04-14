package com.hellom.memory.photo;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class StorageImageDateItemBean extends StorageImageItemBean {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getItemType() {
        return ITEM_TYPE_DATE;
    }
}
