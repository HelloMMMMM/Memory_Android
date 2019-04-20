package com.hellom.mediastore.util.mediastore;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.hellom.mediastore.util.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author:helloM
 * email:1694327880@qq.com
 */
public class MediaStoreUtil {

    private MediaStoreUtil() {
        throw new UnsupportedOperationException("u can not init me...");
    }

    /**
     * 返回根据日期归类的图片信息列表
     *
     * @param context   用于获取ContentResolver
     * @param albumPath 相册路径
     * @param editable  是否可编辑
     * @return 图片信息列表
     */
    public static List<ImageDateSortBean> getDateSortImagesFromStorage(Context context, String albumPath, boolean editable) {
        List<ImageDateSortBean> imageDateSortBeanList = new ArrayList<>();
        List<ImageBean> imageBeanList = null;
        if (context != null) {
            List<ImageBean> imageBeans = getImagesFromStorage(context, albumPath, editable);
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
    public static List<ImageBean> getImagesFromStorage(Context context, String albumPath, boolean editable) {
        List<ImageBean> imageBeanList = new ArrayList<>();
        if (context != null) {
            ContentResolver contentResolver = context.getContentResolver();
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
                while (cursor.moveToNext()) {
                    //图片路径
                    int pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(pathColumnIndex);
                    //满足条件:图片路径存在，文件存在
                    if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                        //图片插入时间
                        int dateColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                        long dateMs = cursor.getLong(dateColumnIndex);
                        String date = DateUtil.getCustomDate(context, dateMs);
                        //满足要求数据添加到列表
                        ImageBean imageBean = new ImageBean();
                        imageBean.setPath(path);
                        imageBean.setDate(date);
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
     * @param activity 用于获取ContentResolver
     * @return 相册列表信息
     */
    public static List<AlbumBean> getAlbumsFromStorage(Activity activity) {
        List<AlbumBean> albumBeans = new ArrayList<>();
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
                    AlbumBean albumBean = new AlbumBean();
                    albumBean.setId(id);
                    albumBean.setAlbumName(albumName);
                    albumBean.setPath(path);
                    albumBeans.add(albumBean);
                }
                cursor.close();
            }
        }
        return albumBeans;
    }
}
