package com.hellom.memory.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * activity的内容容器
     */
    private View content;

    /**
     * status bar的tag
     */
    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initBase();
        initView();
        initListener();
        initData();
    }

    private void initBase() {
        content = ((ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
        //只支持竖屏
        ScreenUtils.setPortrait(this);
        //状态栏默认设置
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0以下，亮暗模式不支持，状态栏文字颜色一直为白色，只有设置黑色背景，才可显示状态栏
            BarUtils.setStatusBarColor(this, Color.BLACK);
        } else {
            //6.0及以上可使用亮暗模式，默认为亮模式(文字颜色为黑色)，并设置状态栏为白色
            BarUtils.setStatusBarLightMode(this, true);
            BarUtils.setStatusBarColor(this, Color.WHITE);
        }
        //设置状态栏背景颜色会向上偏移状态栏的高度，所以设置一下与状态栏高度相等的上部外边距
        addMarginTopEqualStatusBarHeightForContent();
    }

    protected void addMarginTopEqualStatusBarHeightForContent() {
        BarUtils.addMarginTopEqualStatusBarHeight(content);
    }

    protected void subMarginTopEqualStatusBarHeightForContent() {
        BarUtils.subtractMarginTopEqualStatusBarHeight(content);
    }

    protected void setStatusBarVisibility(boolean visible) {
        Window window = getWindow();
        if (visible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View statusBar = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (statusBar != null) {
            statusBar.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    protected void jump(Class target, Bundle bundle, boolean needFinish) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (needFinish) {
            finish();
        }
    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int getLayoutId();
}
