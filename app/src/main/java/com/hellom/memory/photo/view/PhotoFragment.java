package com.hellom.memory.photo.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellom.memory.CustomScrollSpeedGridLayoutManager;
import com.hellom.memory.R;
import com.hellom.memory.base.BaseFragment;
import com.hellom.memory.photo.PhotoContract;
import com.hellom.memory.photo.model.ContentItemBean;
import com.hellom.memory.photo.model.ItemBean;
import com.hellom.memory.photo.presenter.PhotoPresenter;
import com.hellom.memory.preview.PreviewActivity;


public class PhotoFragment extends BaseFragment implements PhotoContract.View {

    private PhotoListAdapter photoListAdapter;

    private PhotoPresenter photoPresenter;

    private RecyclerView photoList;

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
        photoList = layout.findViewById(R.id.photo_list);
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
                switch (view.getId()) {
                    case R.id.iv_photo:
                        FragmentActivity activity = getActivity();
                        if (activity != null) {
                            ContentItemBean contentItemBean = (ContentItemBean) adapter.getData().get(position);
                            Intent intent = new Intent(activity, PreviewActivity.class);
                            intent.putExtra("path", contentItemBean.getPath());
                            startActivity(intent);
                        }
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
        //imageListAdapter.setNewData(photoPresenter.getItems());
        photoListAdapter.setNewData(photoPresenter.getDateSortItems());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }

}
