package com.hellom.memory.media.presenter;

import com.hellom.memory.media.MediaContract;
import com.hellom.memory.media.model.ItemBean;
import com.hellom.memory.media.model.MediaModel;
import com.hellom.memory.media.view.MediaImageFragment;

import java.util.List;

public class MediaPresenter implements MediaContract.Presenter {

    private MediaImageFragment mediaImageFragment;
    private MediaModel mediaModel;

    public MediaPresenter(MediaImageFragment mediaImageFragment) {
        if (mediaImageFragment == null) {
            throw new NullPointerException("v cannot be null!");
        }
        this.mediaImageFragment = mediaImageFragment;

        mediaModel = new MediaModel();
    }

    @Override
    public List<ItemBean> getItems() {
        return mediaModel.getItems(mediaImageFragment.getActivity());
    }

    @Override
    public List<ItemBean> getDateSortItems() {
        return mediaModel.getDateSortItems(mediaImageFragment.getActivity());
    }

    @Override
    public void startPreview() {

    }

    @Override
    public List<String> getSourceData() {
        return mediaModel.getSourceData();
    }
}
