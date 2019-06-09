package com.hellom.memory.photo;

import com.hellom.memory.base.BasePresenter;
import com.hellom.memory.base.BaseView;
import com.hellom.memory.photo.model.ContentItemBean;
import com.hellom.memory.photo.model.ItemBean;

import java.util.List;

public interface PhotoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        List<ItemBean> getDateSortItems();

        List<ContentItemBean> getSourceData();

        int deletePhoto(String uri);

        int getIndexInSourceData(ContentItemBean contentItemBean);
    }
}
