package com.hellom.memory.home;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hellom.memory.R;
import com.hellom.memory.base.BaseActivity;
import com.hellom.memory.media.view.MediaImageFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        List<String> tabs = new ArrayList<>(2);
        tabs.add(getString(R.string.ac_home_tab_photo));
        tabs.add(getString(R.string.ac_home_tab_album));

        List<Fragment> fragments = new ArrayList<>(2);
        fragments.add(MediaImageFragment.newInstance());
        fragments.add(MediaImageFragment.newInstance());
        FragmentPagerAdapter fragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), tabs, fragments);
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }
}
