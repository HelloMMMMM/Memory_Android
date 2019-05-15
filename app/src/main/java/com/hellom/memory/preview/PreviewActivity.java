package com.hellom.memory.preview;

import android.graphics.Color;
import android.os.Build;

import com.blankj.utilcode.util.BarUtils;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;

public class PreviewActivity extends BaseActivity {
    private SubsamplingScaleImageView photoView;

    @Override
    public void initComponent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0以下，亮暗模式不支持，状态栏颜色一直为白色，只有设置背景
            BarUtils.setStatusBarColor(this, Color.BLACK);
        } else {
            BarUtils.setStatusBarLightMode(this, false);
            BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        }
    }

    @Override
    public void initView() {
        photoView = findViewById(R.id.iv_photo);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String path = getIntent().getStringExtra("path");
        //GlideUtil.loadFitCenterOriginImage(this, path, photoView);
        //双击放大
        photoView.setDoubleTapZoomScale(1.0f);
        photoView.setDoubleTapZoomDuration(150);

        photoView.setQuickScaleEnabled(false);

        photoView.setImage(ImageSource.uri(path));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview;
    }
}
