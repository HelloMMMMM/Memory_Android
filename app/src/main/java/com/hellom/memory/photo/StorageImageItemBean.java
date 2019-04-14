package com.hellom.memory.photo;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public abstract class StorageImageItemBean implements MultiItemEntity {
    public static final int ITEM_TYPE_DATE = 1;
    public static final int ITEM_TYPE_CONTENT = 2;

    private int DEFAULT_SPAN_SIZE = 1;
    public static final int MAX_SPAN_SIZE = 4;

    public int getSpanSize() {
        int itemType = getItemType();
        int spanSize = DEFAULT_SPAN_SIZE;
        switch (itemType) {
            case ITEM_TYPE_DATE:
                spanSize = MAX_SPAN_SIZE;
                break;
            case ITEM_TYPE_CONTENT:
                spanSize = DEFAULT_SPAN_SIZE;
                break;
            default:
                break;
        }
        return spanSize;
    }
}
