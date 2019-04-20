package com.hellom.memory.media.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellom.memory.R;
import com.hellom.memory.media.MediaContract;
import com.hellom.memory.media.model.ItemBean;
import com.hellom.memory.media.presenter.MediaPresenter;

public class MediaImageFragment extends Fragment implements MediaContract.View {

    private MediaImageListAdapter imageListAdapter;

    private MediaPresenter mediaPresenter;

    public static MediaImageFragment newInstance() {
        Bundle args = new Bundle();
        MediaImageFragment fragment = new MediaImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_media_image, container, false);
        init(layout);
        return layout;
    }

    private void init(View layout) {
        mediaPresenter = new MediaPresenter(this);

        initView(layout);
        initListener();
        initData();
    }

    private void initView(View layout) {
        RecyclerView imageList = layout.findViewById(R.id.media_image_list);
        GridLayoutManager mGridLayoutManager = new CustomVerticalScrollSpeedGridLayoutManager(getActivity(), ItemBean.MAX_SPAN_SIZE);
        imageList.setLayoutManager(mGridLayoutManager);
        imageListAdapter = new MediaImageListAdapter(null);
        imageListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return imageListAdapter.getData().get(position).getSpanSize();
            }
        });
        imageList.setAdapter(imageListAdapter);
    }

    private void initListener() {
        imageListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_media_image:

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initData() {
        //imageListAdapter.setNewData(mediaPresenter.getItems());
        imageListAdapter.setNewData(mediaPresenter.getDateSortItems());
    }
}
