package com.hellom.memory.photo.model;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class DateItemBean extends ItemBean {

    private String date;

    public String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getItemType() {
        return ITEM_TYPE_DATE;
    }
}
