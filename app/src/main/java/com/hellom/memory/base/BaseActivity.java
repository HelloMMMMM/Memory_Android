package com.hellom.memory.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initBase();
        initComponent();
        initView();
        initListener();
        initData();
    }

    private void initBase() {
        //只支持竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0以下，亮暗模式不支持，状态栏颜色一直为白色，只有设置背景
            BarUtils.setStatusBarColor(this, Color.BLACK);
            //这里设置背景布局会向上偏移状态栏的高度，所以设置一下上部外边距
            ViewGroup content = findViewById(android.R.id.content);
            BarUtils.addMarginTopEqualStatusBarHeight(content.getChildAt(0));
        } else {
            //6.0及以上可直接使用亮暗模式
            BarUtils.setStatusBarLightMode(this, true);
        }
    }

    public abstract void initComponent();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int getLayoutId();
}
