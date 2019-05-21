package com.hellom.memory.preview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;
import com.hellom.memory.component.LinearSpaceItemDecoration;

import java.util.List;

public class PreviewActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager previewPages;
    private PreviewPageAdapter previewPageAdapter;

    private View topBar, bottomBar;

    @Override
    public void initComponent() {
        setStatusBar();
    }

    private void setStatusBar() {
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
        topBar = findViewById(R.id.preview_top_bar);
        bottomBar = findViewById(R.id.preview_bottom_bar);

        previewPages = findViewById(R.id.preview_pages);
        previewPages.setPageMargin(SizeUtils.dp2px(12));
        previewPages.setOffscreenPageLimit(2);
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
    }

    @Override
    public void initData() {
        List<String> srcPaths = getIntent().getStringArrayListExtra("srcPaths");
        int index = getIntent().getIntExtra("index", 0);
        previewPageAdapter = new PreviewPageAdapter(this, srcPaths, previewPages.getOffscreenPageLimit());
        previewPages.setAdapter(previewPageAdapter);
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
                break;
            case R.id.iv_delete:
                break;
            default:
                //do nothing
                break;
        }
    }
}
