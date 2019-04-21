package com.hellom.memory.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initStatusBar();
        initView();
        initListener();
        initData();
    }

    private void initStatusBar() {
        BarUtils.setStatusBarLightMode(this, true);
    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int getLayoutId();
}
