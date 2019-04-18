package com.hellom.memory.photo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hellom.memory.R;
import com.hellom.memory.photo.util.StorageImage.StorageImageBean;
import com.hellom.memory.photo.util.StorageImage.StorageImageContentItemBean;
import com.hellom.memory.photo.util.StorageImage.StorageImageDateSortBean;
import com.hellom.memory.photo.util.StorageImage.StorageImageUtil;

import java.util.ArrayList;
import java.util.List;

public class StorageImageFragment extends Fragment {

    private RecyclerView storageImageList;
    private StorageImageListAdapter storageImageListAdapter;

    public static StorageImageFragment newInstance() {
        Bundle args = new Bundle();
        StorageImageFragment fragment = new StorageImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_storage_image, container, false);

        storageImageList = layout.findViewById(R.id.storage_image_list);
        storageImageList.setLayoutManager(new GridLayoutManager(getActivity(), StorageImageItemBean.MAX_SPAN_SIZE));
        storageImageListAdapter = new StorageImageListAdapter(null);
        storageImageListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return storageImageListAdapter.getData().get(position).getSpanSize();
            }
        });
        storageImageList.setAdapter(storageImageListAdapter);

        List<StorageImageDateSortBean> storageImageDateSortBeans = StorageImageUtil.getDateSortImagesFromStorage(getActivity(), "/storage/emulated/0/DCIM/Camera/", true);
        //List<StorageImageDateSortBean> storageImageDateSortBeans = StorageImageUtil.getDateSortImagesFromStorage(getActivity(), null, true);
        List<StorageImageItemBean> storageImageItemBeans = convertData(storageImageDateSortBeans);
        storageImageListAdapter.setNewData(storageImageItemBeans);

        return layout;
    }

    private List<StorageImageItemBean> convertData(List<StorageImageDateSortBean> storageImageDateSortBeans) {
        List<StorageImageItemBean> storageImageItemBeans = new ArrayList<>();
        for (StorageImageDateSortBean storageImageDateSortBean : storageImageDateSortBeans) {
            StorageImageDateItemBean storageImageDateItemBean = new StorageImageDateItemBean();
            storageImageDateItemBean.setDate(storageImageDateSortBean.getDate());
            storageImageItemBeans.add(storageImageDateItemBean);

            List<StorageImageBean> storageImageBeans = storageImageDateSortBean.getStorageImageBeans();
            for (StorageImageBean storageImageBean : storageImageBeans) {
                StorageImageContentItemBean storageImageContentItemBean = new StorageImageContentItemBean();
                storageImageContentItemBean.setPath(storageImageBean.getPath());
                storageImageItemBeans.add(storageImageContentItemBean);
            }
        }
        return storageImageItemBeans;
    }

    private List<StorageImageItemBean> convertDataNotDateSort(List<StorageImageDateSortBean> storageImageDateSortBeans) {
        List<StorageImageItemBean> storageImageItemBeans = new ArrayList<>();
        for (StorageImageDateSortBean storageImageDateSortBean : storageImageDateSortBeans) {
            List<StorageImageBean> storageImageBeans = storageImageDateSortBean.getStorageImageBeans();
            for (StorageImageBean storageImageBean : storageImageBeans) {
                StorageImageContentItemBean storageImageContentItemBean = new StorageImageContentItemBean();
                storageImageContentItemBean.setPath(storageImageBean.getPath());
                storageImageItemBeans.add(storageImageContentItemBean);
            }
        }
        return storageImageItemBeans;
    }
}
