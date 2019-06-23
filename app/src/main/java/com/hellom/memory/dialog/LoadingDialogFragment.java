package com.hellom.memory.dialog;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;

import com.hellom.memory.R;

public class LoadingDialogFragment extends BaseDialogFragment {
    private TextView loadingContent;

    static LoadingDialogFragment newInstance(String loadingContent) {
        Bundle args = new Bundle();
        args.putString("loadingContent", loadingContent);
        LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view) {
        setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        ContentLoadingProgressBar loadingProgressBar = view.findViewById(R.id.loading_progress);
        loadingProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.c_13227a), PorterDuff.Mode.MULTIPLY);
        loadingContent = view.findViewById(R.id.tv_loading_content);
    }

    @Override
    public void initListener(View view) {

    }

    @Override
    public void initData() {
        Bundle args = getArguments();
        if (args != null) {
            String loadingContentString = args.getString("loadingContent");
            loadingContent.setText(loadingContentString);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_loading;
    }
}
