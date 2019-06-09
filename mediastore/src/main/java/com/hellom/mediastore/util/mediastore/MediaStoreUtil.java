package com.hellom.mediastore.util.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.hellom.mediastore.util.DateUtil;
import com.hellom.mediastore.util.FileUtil;
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

    private static String[] projection;

    static {
        projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT, MediaStore.Images.Media.SIZE};
    }

    private MediaStoreUtil() {
        throw new UnsupportedOperationException("u cannot init me!");
    }

    /**
     * 获取根据日期归类的图片信息列表,不指定相册
     *
     * @param context  用于获取ContentResolver
     * @param isNormal 普通图片或者动图
     * @return 图片信息列表
     */
    public static List<ImageDateSortBean> getDateSortImages(Context context, boolean isNormal) {
        List<ImageDateSortBean> imageDateSortBeans = null;
        if (context != null) {
            List<ImageBean> imageBeans;
            if (isNormal) {
                imageBeans = getNormalImages(context);
            } else {
                imageBeans = getDynamicImages(context);
            }
            imageDateSortBeans = sortImagesByDate(imageBeans);
        }
        return imageDateSortBeans;
    }

    /**
     * 获取根据日期归类的图片信息列表,指定相册
     *
     * @param context   用于获取ContentResolver
     * @param albumPath 相册路径
     * @param isNormal  普通图片或者动图
     * @return 图片信息列表
     */
    public static List<ImageDateSortBean> getDateSortImages(Context context, String albumPath, boolean isNormal) {
        List<ImageDateSortBean> imageDateSortBeans = null;
        if (context != null) {
            List<ImageBean> imageBeans;
            if (isNormal) {
                imageBeans = getNormalImages(context, albumPath);
            } else {
                imageBeans = getDynamicImages(context, albumPath);
            }
            imageDateSortBeans = sortImagesByDate(imageBeans);
        }
        return imageDateSortBeans;
    }

    /**
     * 将图片数据按日期归类
     *
     * @param imageBeans 图片数据
     * @return 按日期归类的图片数据
     */
    private static List<ImageDateSortBean> sortImagesByDate(List<ImageBean> imageBeans) {
        List<ImageDateSortBean> imageDateSortBeans = new ArrayList<>();
        List<ImageBean> tempImageBeans = null;
        for (ImageBean imageBean : imageBeans) {
            String date = imageBean.getDate();
            //判断是否需要新的日期归类
            boolean isNewDate = false;
            if (tempImageBeans == null) {
                isNewDate = true;
            } else {
                int lastIndex = imageDateSortBeans.size() - 1;
                ImageDateSortBean imageDateSortBean = imageDateSortBeans.get(lastIndex);
                if (!imageDateSortBean.getDate().equals(date)) {
                    isNewDate = true;
                }
            }
            //新的日期归类相关集合创建
            if (isNewDate) {
                ImageDateSortBean imageDateSortBean = new ImageDateSortBean();
                tempImageBeans = new ArrayList<>();
                imageDateSortBean.setDate(date);
                imageDateSortBean.setImageBeans(tempImageBeans);
                imageDateSortBeans.add(imageDateSortBean);
            }
            tempImageBeans.add(imageBean);
        }
        return imageDateSortBeans;
    }

    /**
     * 获取图片，不指定相册
     *
     * @param context 上下文
     * @return 图片数据
     */
    public static List<ImageBean> getNormalImages(Context context) {
        return getImages(context, null, true);
    }

    /**
     * 获取指定相册的普通图片
     *
     * @param context   上下文
     * @param albumPath 相册路径
     * @return 图片数据
     */
    public static List<ImageBean> getNormalImages(Context context, String albumPath) {
        return getImages(context, albumPath, true);
    }

    /**
     * 获取动图，不指定相册
     *
     * @param context 上下文
     * @return 图片数据
     */
    public static List<ImageBean> getDynamicImages(Context context) {
        return getImages(context, null, false);
    }

    /**
     * 获取指定相册的动图
     *
     * @param context   上下文
     * @param albumPath 相册路径
     * @return 图片数据
     */
    public static List<ImageBean> getDynamicImages(Context context, String albumPath) {
        return getImages(context, albumPath, false);
    }

    /**
     * 获取图片数据
     *
     * @param context   用于获取content resolver
     * @param albumPath 相册路径，为null不指定相册，否则指定相册
     * @param isNormal  true:普通图片（jpeg、png）;false:动图（gif）
     * @return 图片数据
     */
    private static List<ImageBean> getImages(Context context, String albumPath, boolean isNormal) {
        List<ImageBean> imageBeans = new ArrayList<>();
        if (context != null) {
            ContentResolver contentResolver = context.getContentResolver();
            String selection;
            //normal类型为jpeg、png格式图片；否则为gif图片；
            if (isNormal) {
                selection = MediaStore.Images.Media.MIME_TYPE + " in (?,?)";
            } else {
                selection = MediaStore.Images.Media.MIME_TYPE + " in (image/gif)";
            }
            //若指定相册路径，添加相册的筛选
            if (albumPath != null) {
                selection = selection + " and " + MediaStore.Images.Media.DATA + " like ?";
            }
            Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                    selection, new String[]{"image/jpeg", "image/png", albumPath + "%"},
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
                        String albumId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                        String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                        int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
                        int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                        //添加数据
                        ImageBean imageBean = new ImageBean();
                        imageBean.setId(id);
                        imageBean.setPath(path);
                        imageBean.setDate(date);
                        imageBean.setAlbumId(albumId);
                        imageBean.setAlbumName(albumName);
                        imageBean.setWidth(width);
                        imageBean.setHeight(height);
                        imageBean.setSize(FileUtil.getFileSize(size));
                        imageBean.setType(FileUtil.getFileType(path));
                        imageBeans.add(imageBean);
                    }
                }
                cursor.close();
            }
        }
        return imageBeans;
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
            if (albums.containsKey(imageBean.getAlbumId())) {
                albumBean = albums.get(imageBean.getAlbumId());
                if (albumBean != null) {
                    int imageTotalTemp = albumBean.getImageTotal() + 1;
                    albumBean.setImageTotal(imageTotalTemp);
                }
            } else {
                albumBean = new AlbumBean();
                albumBean.setId(imageBean.getAlbumId());
                albumBean.setAlbumName(imageBean.getAlbumName());
                String previewPath = imageBean.getPath();
                albumBean.setAlbumPreviewPath(previewPath);
                int lastIndex = previewPath.lastIndexOf("/");
                albumBean.setPath(previewPath.substring(0, lastIndex + 1));
                albumBean.setImageTotal(1);
                albums.put(imageBean.getAlbumId(), albumBean);
            }
        }
        List<AlbumBean> albumBeans = new ArrayList<>(albums.size());
        for (String key : albums.keySet()) {
            albumBeans.add(albums.get(key));
        }
        return albumBeans;
    }
}
