package com.hellom.memory.photo.view;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellom.memory.common.CustomScrollSpeedGridLayoutManager;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseFragment;
import com.hellom.memory.eventbus.BaseEvent;
import com.hellom.memory.eventbus.DeletePhotoEvent;
import com.hellom.memory.photo.PhotoContract;
import com.hellom.memory.photo.model.ContentItemBean;
import com.hellom.memory.photo.model.ItemBean;
import com.hellom.memory.photo.presenter.PhotoPresenter;
import com.hellom.memory.preview.PreviewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class PhotoFragment extends BaseFragment implements PhotoContract.View {

    private PhotoPresenter photoPresenter;
    private PhotoListAdapter photoListAdapter;

    public static PhotoFragment newInstance() {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initComponent() {
        photoPresenter = new PhotoPresenter(this);
    }

    @Override
    public void initView(View layout) {
        RecyclerView photoList = layout.findViewById(R.id.photo_list);
        GridLayoutManager mGridLayoutManager = new CustomScrollSpeedGridLayoutManager(getActivity(), ItemBean.MAX_SPAN_SIZE);
        photoList.setLayoutManager(mGridLayoutManager);
        photoListAdapter = new PhotoListAdapter(null);
        photoListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return photoListAdapter.getData().get(position).getSpanSize();
            }
        });
        photoList.setAdapter(photoListAdapter);
    }

    @Override
    public void initListener() {
        photoListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ContentItemBean contentItemBean = (ContentItemBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_photo:
                        startPreview(photoPresenter.getSourceData(), photoPresenter.getIndexInSourceData(contentItemBean));
                        break;
                    default:
                        //do nothing
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        photoListAdapter.setNewData(photoPresenter.getDateSortItems());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {
        if (event instanceof DeletePhotoEvent) {
            deletePhoto(event);
        }
    }

    private void startPreview(List<ContentItemBean> contents, int currentIndex) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("contents", (ArrayList<? extends Parcelable>) contents);
        bundle.putInt("currentIndex", currentIndex);
        jump(PreviewActivity.class, bundle, false);
    }

    private void deletePhoto(BaseEvent event) {
        DeletePhotoEvent deletePhotoEvent = (DeletePhotoEvent) event;
        int listIndex = photoPresenter.deletePhoto(deletePhotoEvent.getUri());
        if (listIndex != -1) {
            photoListAdapter.notifyItemRemoved(listIndex);
        }
    }
}
