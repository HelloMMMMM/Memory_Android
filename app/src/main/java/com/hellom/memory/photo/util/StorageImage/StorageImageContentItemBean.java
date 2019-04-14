package com.hellom.memory.photo.util.StorageImage;

import com.hellom.memory.photo.StorageImageItemBean;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class StorageImageContentItemBean extends StorageImageItemBean {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int getItemType() {
        return ITEM_TYPE_CONTENT;
    }
}
