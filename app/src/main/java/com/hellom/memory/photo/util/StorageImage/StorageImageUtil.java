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
import java.util.Arrays;
import java.util.Collections;
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
    public static List<StorageImageDateSortBean> getDateSortImagesFromStorage(Activity activity, String albumName, boolean editable) {
        List<StorageImageDateSortBean> storageImageDateSortBeanList = new ArrayList<>();
        List<StorageImageBean> storageImageBeanList = null;
        if (activity != null) {
            List<StorageImageBean> storageImageBeans = getImagesFromStorage(activity, albumName, editable);
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
     * 从MediaStore获取图片
     *
     * @param activity  用于获取ContentResolver
     * @param albumPath 相册路径
     * @param editable  是否可编辑
     * @return 图片信息列表
     */
    public static List<StorageImageBean> getImagesFromStorage(Activity activity, String albumPath, boolean editable) {
        List<StorageImageBean> storageImageBeanList = new ArrayList<>();
        if (activity != null) {
            ContentResolver contentResolver = activity.getContentResolver();
            Cursor cursor;
            //设置查找参数
            String[] projection = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN};
            String selection = MediaStore.Images.Media.MIME_TYPE + " in (?,?)";
            //目前最多4个参数
            List<String> selectionArgsList = new ArrayList<>(4);
            selectionArgsList.add("image/jpeg");
            selectionArgsList.add("image/png");
            if (!editable) {
                //不可编辑，才包括gif图片
                selection = MediaStore.Images.Media.MIME_TYPE + " in (?,?,?)";
                selectionArgsList.add("image/gif");
            }
            if (albumPath != null) {
                //指定了相册,添加路径筛选
                selection = selection + " and " + MediaStore.Images.Media.DATA + " like ?";
                selectionArgsList.add("%" + albumPath + "%");
            }
            String[] selectionArgs = new String[selectionArgsList.size()];
            //查找图片
            cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgsList.toArray(selectionArgs), MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
                while (cursor.moveToNext()) {
                    //图片路径
                    int pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(pathColumnIndex);
                    //满足条件:图片路径存在，文件存在
                    if (path != null && FileUtils.isFileExists(path)) {
                        //图片插入时间
                        int dateColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                        long dateMs = cursor.getLong(dateColumnIndex);
                        String date;
                        if (dateMs <= 0) {
                            date = "未知日期";
                        } else {
                            date = TimeUtils.millis2String(dateMs, dateFormat);
                        }
                        //满足要求数据添加到列表
                        StorageImageBean storageImageBean = new StorageImageBean();
                        storageImageBean.setPath(path);
                        storageImageBean.setDate(date);
                        storageImageBeanList.add(storageImageBean);
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
            String[] projection = new String[]{"distinct " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.DATA};
            Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //相册信息
                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                    String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    String pathTemp = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    int lastCharIndex = pathTemp.lastIndexOf("/");
                    String path = pathTemp.substring(0, lastCharIndex);
                    //添加到相册列表
                    StorageAlbumBean storageAlbumBean = new StorageAlbumBean();
                    storageAlbumBean.setId(id);
                    storageAlbumBean.setAlbumName(albumName);
                    storageAlbumBean.setPath(path);
                    storageAlbumBeans.add(storageAlbumBean);
                }
                cursor.close();
            }
        }
        return storageAlbumBeans;
    }
}
