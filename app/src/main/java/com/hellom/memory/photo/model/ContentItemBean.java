package com.hellom.memory.photo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class ContentItemBean extends ItemBean implements Parcelable {
    private String date;
    private String time;
    private String type;
    private int width;
    private int height;
    private String size;
    private String uri;

    public static Creator<ContentItemBean> getCREATOR() {
        return CREATOR;
    }

    public String getUri() {
        return uri;
    }

    void setUri(String uri) {
        this.uri = uri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getItemType() {
        return ITEM_TYPE_CONTENT;
    }

    ContentItemBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.type);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.size);
        dest.writeString(this.uri);
    }

    protected ContentItemBean(Parcel in) {
        this.date = in.readString();
        this.time = in.readString();
        this.type = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.size = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<ContentItemBean> CREATOR = new Creator<ContentItemBean>() {
        @Override
        public ContentItemBean createFromParcel(Parcel source) {
            return new ContentItemBean(source);
        }

        @Override
        public ContentItemBean[] newArray(int size) {
            return new ContentItemBean[size];
        }
    };
}
