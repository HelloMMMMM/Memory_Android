package com.hellom.mediastore.util.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.hellom.mediastore.util.DateUtil;
import com.hellom.mediastore.util.mediastore.bean.AlbumBean;
import com.hellom.mediastore.util.mediastore.bean.ImageBean;
import com.hellom.mediastore.util.mediastore.bean.ImageDateSortBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class MediaStoreUtil {

    private MediaStoreUtil() {
        throw new UnsupportedOperationException("u cannot init me!");
    }

    /**
     * 返回根据日期归类的图片信息列表
     *
     * @param context   用于获取ContentResolver
     * @param albumPath 相册路径
     * @param editable  是否可编辑
     * @return 图片信息列表
     */
    public static List<ImageDateSortBean> getDateSortImages(Context context, String albumPath, boolean editable) {
        List<ImageDateSortBean> imageDateSortBeanList = new ArrayList<>();
        List<ImageBean> imageBeanList = null;
        if (context != null) {
            List<ImageBean> imageBeans = getImages(context, albumPath, editable);
            for (ImageBean imageBean : imageBeans) {
                String date = imageBean.getDate();
                //判断是否需要添加新的日期归类
                boolean isNewDate = false;
                if (imageBeanList == null) {
                    isNewDate = true;
                } else {
                    int lastIndex = imageDateSortBeanList.size() - 1;
                    ImageDateSortBean imageDateSortBean = imageDateSortBeanList.get(lastIndex);
                    if (!imageDateSortBean.getDate().equals(date)) {
                        isNewDate = true;
                    }
                }
                //新的日期归类相关集合创建
                if (isNewDate) {
                    ImageDateSortBean imageDateSortBean = new ImageDateSortBean();
                    imageBeanList = new ArrayList<>();
                    imageDateSortBean.setDate(date);
                    imageDateSortBean.setImageBeans(imageBeanList);
                    imageDateSortBeanList.add(imageDateSortBean);
                }
                imageBeanList.add(imageBean);
            }
        }
        return imageDateSortBeanList;
    }

    /**
     * 从MediaStore获取图片
     *
     * @param context   用于获取ContentResolver
     * @param albumPath 相册路径
     * @param editable  是否可编辑
     * @return 图片信息列表
     */
    public static List<ImageBean> getImages(Context context, String albumPath, boolean editable) {
        List<ImageBean> imageBeanList = new ArrayList<>();
        if (context != null) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor;
            //设置查找参数
            String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_ADDED};
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
                selectionArgsList.add(albumPath + "%");
            }
            String[] selectionArgs = new String[selectionArgsList.size()];
            //查找图片
            cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                    selection, selectionArgsList.toArray(selectionArgs),
                    MediaStore.Images.Media.DATE_ADDED + " DESC");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //图片路径
                    int pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(pathColumnIndex);
                    //满足条件:图片路径存在，文件存在
                    if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                        //图片信息
                        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                        int dateColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
                        long dateSecond = cursor.getLong(dateColumnIndex);
                        String date = DateUtil.getDefaultDate(context, DateUtil.convertMs(dateSecond));
                        String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                        //满足要求数据添加到列表
                        ImageBean imageBean = new ImageBean();
                        imageBean.setId(id);
                        imageBean.setPath(path);
                        imageBean.setDate(date);
                        imageBean.setBucketId(bucketId);
                        imageBean.setBucketDisplayName(bucketDisplayName);
                        imageBeanList.add(imageBean);
                    }
                }
                cursor.close();
            }
        }
        return imageBeanList;
    }

    /**
     * 返回相册列表信息
     *
     * @param context 用于获取ContentResolver
     * @return 相册列表信息
     */
    public static List<AlbumBean> getAlbums(Context context) {
        List<ImageBean> imageBeans = getImages(context, null, false);
        Map<String, AlbumBean> albums = new HashMap<>();
        for (ImageBean imageBean : imageBeans) {
            AlbumBean albumBean;
            if (albums.containsKey(imageBean.getBucketId())) {
                albumBean = albums.get(imageBean.getBucketId());
                if (albumBean != null) {
                    int imageTotalTemp = albumBean.getImageTotal() + 1;
                    albumBean.setImageTotal(imageTotalTemp);
                }
            } else {
                albumBean = new AlbumBean();
                albumBean.setId(imageBean.getBucketId());
                albumBean.setAlbumName(imageBean.getBucketDisplayName());
                String previewPath = imageBean.getPath();
                albumBean.setAlbumPreviewPath(previewPath);
                int lastIndex = previewPath.lastIndexOf("/");
                albumBean.setPath(previewPath.substring(0, lastIndex + 1));
                albumBean.setImageTotal(1);
                albums.put(imageBean.getBucketId(), albumBean);
            }
        }
        List<AlbumBean> albumBeans = new ArrayList<>(albums.size());
        for (String key : albums.keySet()) {
            albumBeans.add(albums.get(key));
        }
        return albumBeans;
    }
}
