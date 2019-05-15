package com.hellom.memory.home;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hellom.memory.R;
import com.hellom.memory.album.AlbumFragment;
import com.hellom.memory.base.BaseActivity;
import com.hellom.memory.photo.view.PhotoFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private final int TAB_PHOTO = 1;
    private final int TAB_ALBUM = 2;
    private final int TAB_DISCOVER = 3;
    private final int TAB_MINE = 4;

    private ViewGroup photoTab, albumTab, discoverTab, mineTab;
    private ViewGroup currentTab;
    private Fragment photoFragment, albumFragment;

    @Override
    public void initComponent() {

    }

    @Override
    public void initView() {
        photoTab = findViewById(R.id.tab_photos);
        albumTab = findViewById(R.id.tab_albums);
        discoverTab = findViewById(R.id.tab_discover);
        mineTab = findViewById(R.id.tab_mine);
    }

    @Override
    public void initListener() {
        photoTab.setOnClickListener(this);
        albumTab.setOnClickListener(this);
        discoverTab.setOnClickListener(this);
        mineTab.setOnClickListener(this);
    }

    @Override
    public void initData() {
        switchTab(photoTab, TAB_PHOTO);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_photos:
                switchTab(photoTab, TAB_PHOTO);
                break;
            case R.id.tab_albums:
                switchTab(albumTab, TAB_ALBUM);
                break;
            case R.id.tab_discover:
                switchTab(discoverTab, TAB_DISCOVER);
                break;
            case R.id.tab_mine:
                switchTab(mineTab, TAB_MINE);
                break;
            default:
                //do nothing
                break;
        }
    }

    private void switchTab(ViewGroup tab, int tabIndex) {
        if (tab != null && !tab.equals(currentTab)) {
            setTabSelectedOrReset(currentTab, false);
            setTabSelectedOrReset(tab, true);
            setFragmentWhenSelected(tabIndex);
        }
    }

    private void setTabSelectedOrReset(ViewGroup tab, boolean isSelected) {
        if (tab != null) {
            if (isSelected) {
                currentTab = tab;
            }
            tab.getChildAt(0).setSelected(isSelected);
            tab.getChildAt(1).setSelected(isSelected);
        }
    }

    private void setFragmentWhenSelected(int tabIndex) {
        Fragment currentFragment = null;
        switch (tabIndex) {
            case TAB_PHOTO:
                currentFragment = photoFragment == null ? (photoFragment = PhotoFragment.newInstance()) : photoFragment;
                break;
            case TAB_ALBUM:
                currentFragment = albumFragment == null ? (albumFragment = AlbumFragment.newInstance()) : albumFragment;
                break;
            case TAB_DISCOVER:
                break;
            case TAB_MINE:
                break;
            default:
                //do nothing
                break;
        }
        if (currentFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, currentFragment);
            fragmentTransaction.commit();
        }
    }
}
