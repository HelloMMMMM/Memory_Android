package com.hellom.memory.wallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;
import com.hellom.memory.dialog.DialogFactory;
import com.hellom.memory.dialog.LoadingDialogFragment;

import java.io.IOException;

public class WallPaperActivity extends BaseActivity implements View.OnClickListener {
    private SubsamplingScaleImageView wallPaperPreview;

    private LoadingDialogFragment loadingDialogFragment;

    @Override
    public void initComponent() {

    }

    @Override
    public void initView() {
        settingAboutStatusBar();
        wallPaperPreview = findViewById(R.id.wallpaper_preview);
    }

    @Override
    public void initListener() {
        findViewById(R.id.tv_rotate).setOnClickListener(this);
        findViewById(R.id.tv_set_wallpaper).setOnClickListener(this);
    }

    @Override
    public void initData() {
        String imageUri = getIntent().getStringExtra("uri");
        wallPaperPreview.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        wallPaperPreview.setImage(ImageSource.uri(imageUri));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallpaper;
    }

    private void settingAboutStatusBar() {
        //预览界面特殊处理(状态栏透明，图片可全屏预览,topbar向下偏移)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        subMarginTopEqualStatusBarHeightForContent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上状态栏改为暗模式
            BarUtils.setStatusBarLightMode(this, false);
        }
        setStatusBarVisibility(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rotate:
                changeOrientation();
                break;
            case R.id.tv_set_wallpaper:
                setWallpaperOption();
                break;
        }
    }

    private void changeOrientation() {
        switch (wallPaperPreview.getOrientation()) {
            case SubsamplingScaleImageView.ORIENTATION_0:
                wallPaperPreview.setOrientation(SubsamplingScaleImageView.ORIENTATION_90);
                break;
            case SubsamplingScaleImageView.ORIENTATION_90:
                wallPaperPreview.setOrientation(SubsamplingScaleImageView.ORIENTATION_180);
                break;
            case SubsamplingScaleImageView.ORIENTATION_180:
                wallPaperPreview.setOrientation(SubsamplingScaleImageView.ORIENTATION_270);
                break;
            case SubsamplingScaleImageView.ORIENTATION_270:
                wallPaperPreview.setOrientation(SubsamplingScaleImageView.ORIENTATION_0);
                break;
        }
    }

    private void setWallpaperOption() {
        showLoading();
        ThreadUtils.executeBySingle(new ThreadUtils.Task<Object>() {

            @Nullable
            @Override
            public Object doInBackground() throws Throwable {
                setWallpaper();
                return null;
            }

            @Override
            public void onSuccess(@Nullable Object result) {
                dismissLoading();
                ToastUtils.showShort(getString(R.string.wallpaper_preview_set_wallaper_success));
                finish();
            }

            @Override
            public void onCancel() {
                dismissLoading();
                ToastUtils.showShort(getString(R.string.wallpaper_preview_set_wallaper_fail));
            }

            @Override
            public void onFail(Throwable t) {
                dismissLoading();
                ToastUtils.showShort(getString(R.string.wallpaper_preview_set_wallaper_fail));
                LogUtils.e(t.fillInStackTrace());
            }
        });
    }

    private void setWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap tempBitmap = ImageUtils.view2Bitmap(wallPaperPreview);
        try {
            wallpaperManager.setBitmap(tempBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLoading() {
        loadingDialogFragment = DialogFactory.createLoadingDialog(getSupportFragmentManager(),
                getString(R.string.wallpaper_preview_set_wallpaper_loading_content));
    }

    private void dismissLoading() {
        loadingDialogFragment.dismiss();
    }
}
