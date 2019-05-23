package com.hellom.memory.preview;

import android.app.WallpaperManager;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PreviewActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager previewPages;
    private PreviewPageAdapter previewPageAdapter;

    private View topBar, bottomBar;


    @Override
    public void initView() {
        topBar = findViewById(R.id.preview_top_bar);
        settingAboutStatusBar();
        bottomBar = findViewById(R.id.preview_bottom_bar);
        previewPages = findViewById(R.id.preview_pages);
        previewPages.setPageMargin(SizeUtils.dp2px(12));
        previewPages.setOffscreenPageLimit(2);
        previewPageAdapter = new PreviewPageAdapter(this, null, previewPages.getOffscreenPageLimit());
        previewPages.setAdapter(previewPageAdapter);
    }

    @Override
    public void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_photo_info).setOnClickListener(this);
        findViewById(R.id.iv_menu).setOnClickListener(this);
        findViewById(R.id.iv_set_wallpaper).setOnClickListener(this);
        findViewById(R.id.iv_delete).setOnClickListener(this);

        previewPages.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        previewPageAdapter.setOnItemClickListener(new PreviewPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                setBarVisibleOrGone();
            }
        });
    }

    @Override
    public void initData() {
        List<String> srcUris = getIntent().getStringArrayListExtra("srcPaths");
        int index = getIntent().getIntExtra("index", 0);
        previewPageAdapter.setSrcUris(srcUris);
        previewPages.setCurrentItem(index, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_photo_info:
                break;
            case R.id.iv_menu:
                break;
            case R.id.iv_set_wallpaper:
                setWallpaper();
                break;
            case R.id.iv_delete:
                break;
            default:
                //do nothing
                break;
        }
    }

    private void settingAboutStatusBar() {
        //预览界面特殊处理(状态栏透明，图片可全屏预览,topbar向下偏移)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        subMarginTopEqualStatusBarHeightForContent();
        BarUtils.addMarginTopEqualStatusBarHeight(topBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上状态栏改为暗模式
            BarUtils.setStatusBarLightMode(this, false);
        }
    }

    private void setBarVisibleOrGone() {
        final boolean visable = !(topBar.getVisibility() == View.VISIBLE);
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visable) {
                    topBar.setVisibility(View.VISIBLE);
                    bottomBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!visable) {
                    topBar.setVisibility(View.GONE);
                    bottomBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        Animation topAnimation = visable ? AnimationUtils.loadAnimation(this, R.anim.anim_preview_top_enter)
                : AnimationUtils.loadAnimation(this, R.anim.anim_preview_top_exit);
        Animation bottomAnimation = visable ? AnimationUtils.loadAnimation(this, R.anim.anim_preview_bottom_enter)
                : AnimationUtils.loadAnimation(this, R.anim.anim_preview_bottom_exit);
        topAnimation.setAnimationListener(animationListener);
        topBar.startAnimation(topAnimation);
        bottomBar.startAnimation(bottomAnimation);
        setStatusBarVisibility(visable);
    }

    private void setWallpaper() {
        String imageUri = previewPageAdapter.getCurrentItemData(previewPages.getCurrentItem());
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setStream(new FileInputStream(imageUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
