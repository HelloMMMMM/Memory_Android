package com.hellom.memory.wallpaper;

import android.graphics.Color;
import android.os.Build;

import com.blankj.utilcode.util.BarUtils;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;

public class WallPaperActivity extends BaseActivity {
    private SubsamplingScaleImageView wallPaperPreview;

    @Override
    public void initView() {
        settingAboutStatusBar();
        wallPaperPreview = findViewById(R.id.wallpaper_preview);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String imageUri = getIntent().getStringExtra("uri");
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
}
