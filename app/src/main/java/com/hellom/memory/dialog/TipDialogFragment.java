package com.hellom.memory.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hellom.memory.R;

public class TipDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private TextView tipContent;
    private Callback callback;

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    static TipDialogFragment newInstance(String tipContent) {
        Bundle args = new Bundle();
        args.putString("tipContent", tipContent);
        TipDialogFragment fragment = new TipDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view) {
        tipContent = view.findViewById(R.id.tv_tip_content);
    }

    @Override
    public void initListener(View view) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_sure).setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle args = getArguments();
        if (args != null) {
            String tipContentString = args.getString("tipContent");
            tipContent.setText(tipContentString);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_tip;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (callback != null) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    callback.cancel();
                    break;
                case R.id.tv_sure:
                    callback.sure();
                    break;
                default:
                    //do nothing
                    break;
            }
        }
    }

    public interface Callback {
        void cancel();

        void sure();
    }
}
