package com.hellom.memory.preview;

import android.app.WallpaperManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;
import com.hellom.memory.dialog.DialogFactory;
import com.hellom.memory.dialog.TipDialogFragment;
import com.hellom.memory.eventbus.DeletePhotoEvent;
import com.hellom.memory.photo.model.ContentItemBean;
import com.hellom.memory.wallpaper.WallPaperActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PreviewActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager previewPages;
    private PreviewPageAdapter previewPageAdapter;

    private View topBar, bottomBar;
    private TextView photoTitle;

    @Override
    public void initComponent() {

    }

    @Override
    public void initView() {
        topBar = findViewById(R.id.preview_top_bar);
        photoTitle = findViewById(R.id.tv_photo_title);
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
                setPhotoTitle(position);
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
        List<ContentItemBean> contents = getIntent().getParcelableArrayListExtra("contents");
        int currentIndex = getIntent().getIntExtra("currentIndex", 0);
        previewPageAdapter.setSrcUris(contents);
        if (currentIndex == 0) {
            setPhotoTitle(0);
        } else {
            previewPages.setCurrentItem(currentIndex, false);
        }
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
                showPhotoInfoDialog();
                break;
            case R.id.iv_menu:
                break;
            case R.id.iv_set_wallpaper:
                jumpToSetWallpaper();
                break;
            case R.id.iv_delete:
                showDeleteTip();
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
        topBar.setPadding(0, BarUtils.getStatusBarHeight(), 0, topBar.getPaddingBottom());
        //BarUtils.addMarginTopEqualStatusBarHeight(topBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上状态栏改为暗模式
            BarUtils.setStatusBarLightMode(this, false);
        }
    }

    private void setPhotoTitle(int position) {
        ContentItemBean contentItemBean = previewPageAdapter.getItemData(position);
        photoTitle.setText(String.format("%s  %s", contentItemBean.getDate(), contentItemBean.getTime()));
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

    private void showPhotoInfoDialog() {
        DialogFactory.createPhotoInfoDialog(getSupportFragmentManager(),
                previewPageAdapter.getItemData(previewPages.getCurrentItem()));
    }

    private void jumpToSetWallpaper() {
        Bundle bundle = new Bundle();
        bundle.putString("uri",
                previewPageAdapter.getItemData(previewPages.getCurrentItem()).getUri());
        jump(WallPaperActivity.class, bundle, false);
    }

    private void showDeleteTip() {
        DialogFactory.createTipDialog(getSupportFragmentManager(),
                getString(R.string.delete_photo_tip_content), new TipDialogFragment.Callback() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void sure() {
                        deletePhoto();
                    }
                });
    }

    private void deletePhoto() {
        //FileUtils.delete(previewPageAdapter.getCurrentItemData(previewPages.getCurrentItem()).getUri());
        ContentItemBean contentItemBean = previewPageAdapter.getItemData(previewPages.getCurrentItem());
        previewPageAdapter.deleteCurrentItemData(previewPages.getCurrentItem());
        EventBus.getDefault().post(new DeletePhotoEvent(contentItemBean.getUri()));
    }
}
