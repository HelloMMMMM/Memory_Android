package com.hellom.memory.wallpaper;

import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;

public class WallPaperActivity extends BaseActivity implements View.OnClickListener {
    private SubsamplingScaleImageView wallPaperPreview;

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
}
