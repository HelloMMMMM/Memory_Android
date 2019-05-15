package com.hellom.memory.photo.presenter;

import com.hellom.memory.photo.PhotoContract;
import com.hellom.memory.photo.model.ItemBean;
import com.hellom.memory.photo.model.PhotoModel;
import com.hellom.memory.photo.view.PhotoFragment;

import java.util.List;

public class PhotoPresenter implements PhotoContract.Presenter {

    private PhotoFragment photoFragment;
    private PhotoModel photoModel;

    public PhotoPresenter(PhotoFragment photoFragment) {
        if (photoFragment == null) {
            throw new NullPointerException("v cannot be null!");
        }
        this.photoFragment = photoFragment;

        photoModel = new PhotoModel();
    }

    @Override
    public List<ItemBean> getItems() {
        return photoModel.getItems(photoFragment.getActivity());
    }

    @Override
    public List<ItemBean> getDateSortItems() {
        return photoModel.getDateSortItems(photoFragment.getActivity());
    }

    @Override
    public void startPreview() {

    }

    @Override
    public List<String> getSourceData() {
        return photoModel.getSourceData();
    }
}
