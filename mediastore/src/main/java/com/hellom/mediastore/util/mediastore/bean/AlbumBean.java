package com.hellom.mediastore.util.mediastore.bean;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class AlbumBean {
    private String id;
    private String albumName;
    private int imageTotal = 0;
    private String path;
    private String albumPreviewPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getImageTotal() {
        return imageTotal;
    }

    public void setImageTotal(int imageTotal) {
        this.imageTotal = imageTotal;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbumPreviewPath() {
        return albumPreviewPath;
    }

    public void setAlbumPreviewPath(String albumPreviewPath) {
        this.albumPreviewPath = albumPreviewPath;
    }
}
