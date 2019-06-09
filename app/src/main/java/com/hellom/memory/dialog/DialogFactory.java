package com.hellom.memory.dialog;

import androidx.fragment.app.FragmentManager;

import com.hellom.memory.photo.model.ContentItemBean;

public class DialogFactory {

    private DialogFactory() {
        throw new UnsupportedOperationException("u cannot init me!");
    }

    public static void createPhotoInfoDialog(FragmentManager fragmentManager, ContentItemBean contentItemBean) {
        PhotoInfoDialogFragment photoInfoDialogFragment = PhotoInfoDialogFragment.newInstance(contentItemBean);
        photoInfoDialogFragment.show(fragmentManager, null);
    }

    public static void createTipDialog(FragmentManager fragmentManager, String tipContent, TipDialogFragment.Callback callback) {
        TipDialogFragment tipDialogFragment = TipDialogFragment.newInstance(tipContent);
        tipDialogFragment.setCallback(callback);
        tipDialogFragment.show(fragmentManager, null);
    }
}
