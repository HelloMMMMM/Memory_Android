package com.hellom.memory.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hellom.memory.R;
import com.hellom.memory.photo.model.ContentItemBean;

public class PhotoInfoDialogFragment extends BaseDialogFragment {
    private TextView date;
    private TextView type;
    private TextView dimensions;
    private TextView size;
    private TextView path;

    static PhotoInfoDialogFragment newInstance(ContentItemBean contentItemBean) {
        Bundle args = new Bundle();
        args.putParcelable("content", contentItemBean);
        PhotoInfoDialogFragment fragment = new PhotoInfoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view) {
        date = view.findViewById(R.id.tv_date);
        type = view.findViewById(R.id.tv_type);
        dimensions = view.findViewById(R.id.tv_dimensions);
        size = view.findViewById(R.id.tv_size);
        path = view.findViewById(R.id.tv_path);
    }

    @Override
    public void initListener(View view) {

    }

    @Override
    public void initData() {
        Bundle args = getArguments();
        if (args != null) {
            ContentItemBean contentItemBean = args.getParcelable("content");
            if (contentItemBean != null) {
                date.setText(contentItemBean.getDate());
                type.setText(contentItemBean.getType());
                dimensions.setText(getString(R.string.dialog_photo_info_dimensions_format,
                        contentItemBean.getWidth(), contentItemBean.getHeight()));
                size.setText(contentItemBean.getSize());
                path.setText(contentItemBean.getUri());
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_photo_info;
    }
}
