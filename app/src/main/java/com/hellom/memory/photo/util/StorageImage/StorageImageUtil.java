package com.hellom.memory.photo.util.StorageImage;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class StorageImageUtil {

    private StorageImageUtil() {
        throw new UnsupportedOperationException("u can not init me...");
    }

    /**
     * 获取图片列表，并根据日期分类
     */
    public static List<StorageImageDateSortBean> getDateSortImagesFromStorage(Activity activity, String albumId, String albumName) {
        List<StorageImageDateSortBean> storageImageDateSortBeanList = new ArrayList<>();
        List<StorageImageBean> storageImageBeanList = null;
        if (activity != null) {
            List<StorageImageBean> storageImageBeans = getImagesFromStorage(activity, albumId, albumName);
            for (StorageImageBean storageImageBean : storageImageBeans) {
                String date = storageImageBean.getDate();
                //判断是否需要添加新的日期归类
                boolean isNewDate = false;
                if (storageImageBeanList == null) {
                    isNewDate = true;
                } else {
                    int lastIndex = storageImageDateSortBeanList.size() - 1;
                    StorageImageDateSortBean storageImageDateSortBean = storageImageDateSortBeanList.get(lastIndex);
                    if (!storageImageDateSortBean.getDate().equals(date)) {
                        isNewDate = true;
                    }
                }
                //新的日期归类相关集合创建
                if (isNewDate) {
                    StorageImageDateSortBean storageImageDateSortBean = new StorageImageDateSortBean();
                    storageImageBeanList = new ArrayList<>();
                    storageImageDateSortBean.setDate(date);
                    storageImageDateSortBean.setStorageImageBeans(storageImageBeanList);
                    storageImageDateSortBeanList.add(storageImageDateSortBean);
                }
                storageImageBeanList.add(storageImageBean);
            }
        }
        return storageImageDateSortBeanList;
    }

    /**
     * 获取图片列表
     */
    public static List<StorageImageBean> getImagesFromStorage(Activity activity, String albumId, String albumName) {
        List<StorageImageBean> storageImageBeanList = new ArrayList<>();
        if (activity != null) {
            ContentResolver contentResolver = activity.getContentResolver();
            Cursor cursor;
            //设置查找参数
            String[] projection = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN};
            String selection = null;
            String[] selectionArgs = null;
            if (albumId != null && albumName != null) {
                //指定了相册
                selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=? and " + MediaStore.Images.Media.BUCKET_ID + "=?";
                selectionArgs = new String[]{albumName, albumId};
            }
            //查找图片
            cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, selectionArgs,
                    MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                while (cursor.moveToNext()) {
                    //图片路径
                    int pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(pathColumnIndex);
                    //满足条件:图片路径存在，文件存在
                    if (path != null && FileUtils.isFileExists(path)) {
                        //图片拍摄时间
                        int dateColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                        long dateMs = cursor.getLong(dateColumnIndex);
                        String date = TimeUtils.millis2String(dateMs, dateFormat);
                        //满足要求数据添加到列表
                        StorageImageBean storageImageBean = new StorageImageBean();
                        storageImageBean.setPath(path);
                        storageImageBean.setDate(date);
                        storageImageBeanList.add(storageImageBean);
                        Log.e("mx", storageImageBean.getPath());
                    }
                }
                cursor.close();
            }
        }
        return storageImageBeanList;
    }

    /**
     * 获取相册列表
     *
     * @return 相册列表
     */
    public static List<StorageAlbumBean> getAlbumsFromStorage(Activity activity) {
        List<StorageAlbumBean> storageAlbumBeans = new ArrayList<>();
        if (activity != null) {
            ContentResolver contentResolver = activity.getContentResolver();
            Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{"distinct " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID},
                    null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //相册信息
                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                    String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    //添加到相册列表
                    StorageAlbumBean storageAlbumBean = new StorageAlbumBean();
                    storageAlbumBean.setId(id);
                    storageAlbumBean.setAlbumName(albumName);
                    storageAlbumBeans.add(storageAlbumBean);
                }
                cursor.close();
            }
        }
        return storageAlbumBeans;
    }
}
