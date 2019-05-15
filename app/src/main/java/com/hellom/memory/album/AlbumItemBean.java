package com.hellom.memory.album;

import com.hellom.mediastore.util.mediastore.bean.AlbumBean;

import java.util.List;

public class AlbumItemBean {
    public static final int TYPE_INTELLIGENT = 1;
    public static final int TYPE_SORT = 2;
    public static final int TYPE_PERSONAL = 3;

    private int albumType;
    private String albumTypeTitle;
    private List<AlbumBean> albumBeans;

    public int getAlbumType() {
        return albumType;
    }

    public void setAlbumType(int albumType) {
        this.albumType = albumType;
    }

    public String getAlbumTypeTitle() {
        return albumTypeTitle;
    }

    public void setAlbumTypeTitle(String albumTypeTitle) {
        this.albumTypeTitle = albumTypeTitle;
    }

    public List<AlbumBean> getAlbumBeans() {
        return albumBeans;
    }

    public void setAlbumBeans(List<AlbumBean> albumBeans) {
        this.albumBeans = albumBeans;
    }
}
