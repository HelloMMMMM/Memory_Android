package com.hellom.memory.album;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hellom.mediastore.util.mediastore.MediaStoreUtil;
import com.hellom.mediastore.util.mediastore.bean.AlbumBean;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends BaseFragment {

    private AlbumListSortAdapter albumListSortAdapter;

    public static AlbumFragment newInstance() {
        Bundle args = new Bundle();
        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void initView(View layout) {
        RecyclerView albumList = layout.findViewById(R.id.album_list);
        albumList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        albumListSortAdapter = new AlbumListSortAdapter(R.layout.layout_album_list_item);
        albumList.setAdapter(albumListSortAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        List<AlbumItemBean> albumItemBeans = new ArrayList<>();
        AlbumItemBean intelligentAlbumItemBean = new AlbumItemBean();
        intelligentAlbumItemBean.setAlbumType(AlbumItemBean.TYPE_INTELLIGENT);
        intelligentAlbumItemBean.setAlbumTypeTitle(getString(R.string.fg_album_intelligent_album));
        List<AlbumBean> intelligentAlbumBeans = new ArrayList<>();
        AlbumBean personAlbumBean = new AlbumBean();
        personAlbumBean.setAlbumName(getString(R.string.fg_album_intelligent_album_person));
        AlbumBean thingAlbumBean = new AlbumBean();
        thingAlbumBean.setAlbumName(getString(R.string.fg_album_intelligent_album_thing));
        AlbumBean locationAlbumBean = new AlbumBean();
        locationAlbumBean.setAlbumName(getString(R.string.fg_album_intelligent_album_location));
        intelligentAlbumBeans.add(personAlbumBean);
        intelligentAlbumBeans.add(thingAlbumBean);
        intelligentAlbumBeans.add(locationAlbumBean);
        intelligentAlbumItemBean.setAlbumBeans(intelligentAlbumBeans);

        AlbumItemBean sortAlbumItemBean = new AlbumItemBean();
        sortAlbumItemBean.setAlbumTypeTitle(getString(R.string.fg_album_sort_album));
        sortAlbumItemBean.setAlbumBeans(MediaStoreUtil.getAlbums(getActivity()));

        AlbumItemBean personalAlbumItemBean = new AlbumItemBean();
        personalAlbumItemBean.setAlbumTypeTitle(getString(R.string.fg_album_personal_album));
        List<AlbumBean> personalAlbumBeans = new ArrayList<>();
        AlbumBean testAlbumBean = new AlbumBean();
        testAlbumBean.setAlbumName("test");
        personalAlbumBeans.add(testAlbumBean);
        personalAlbumItemBean.setAlbumBeans(personalAlbumBeans);

        albumItemBeans.add(intelligentAlbumItemBean);
        albumItemBeans.add(sortAlbumItemBean);
        albumItemBeans.add(personalAlbumItemBean);

        albumListSortAdapter.setNewData(albumItemBeans);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_album;
    }
}
