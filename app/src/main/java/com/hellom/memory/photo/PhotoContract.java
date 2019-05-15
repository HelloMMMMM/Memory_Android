package com.hellom.memory.photo;

import com.hellom.memory.base.BasePresenter;
import com.hellom.memory.base.BaseView;
import com.hellom.memory.photo.model.ItemBean;

import java.util.List;

public interface PhotoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        List<ItemBean> getItems();

        List<ItemBean> getDateSortItems();

        void startPreview();

        List<String> getSourceData();
    }
}
