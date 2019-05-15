package com.hellom.memory.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(getLayoutId(), container, false);
        initComponent();
        initView(layout);
        initListener();
        initData();
        return layout;
    }

    public abstract void initComponent();

    public abstract void initView(View layout);

    public abstract void initListener();

    public abstract void initData();

    public abstract int getLayoutId();
}
