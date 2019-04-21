package com.hellom.memory.media;

import com.hellom.memory.base.BasePresenter;
import com.hellom.memory.base.BaseView;
import com.hellom.memory.media.model.ItemBean;

import java.util.List;

public interface MediaContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        List<ItemBean> getItems();

        List<ItemBean> getDateSortItems();

        void startPreview();

        List<String> getSourceData();
    }
}
