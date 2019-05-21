package com.hellom.memory.photo.model;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class ContentItemBean extends ItemBean {
    /**
     * 在资源列表中的位置索引
     */
    private int index;

    private String uri;

    public int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    public String getUri() {
        return uri;
    }

    void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public int getItemType() {
        return ITEM_TYPE_CONTENT;
    }
}
