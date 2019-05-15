package com.hellom.memory.photo.model;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class ContentItemBean extends ItemBean {
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
