package com.hellom.memory.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

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
        ScreenUtils.setPortrait(this);
        //状态栏设置
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0以下，亮暗模式不支持，状态栏颜色一直为白色，只有设置背景
            BarUtils.setStatusBarColor(this, Color.BLACK);
        } else {
            //6.0及以上可直接使用亮暗模式
            BarUtils.setStatusBarLightMode(this, true);
            BarUtils.setStatusBarColor(this, Color.WHITE);
        }
        //这里设置背景布局会向上偏移状态栏的高度，所以设置一下上部外边距
        ViewGroup content = findViewById(android.R.id.content);
        BarUtils.addMarginTopEqualStatusBarHeight(content.getChildAt(0));
    }

    public abstract void initComponent();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int getLayoutId();
}
