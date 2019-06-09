package com.hellom.memory.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class BaseDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setNoTitle();
        View layout = inflater.inflate(getLayoutId(), container, false);
        initView(layout);
        initListener(layout);
        initData();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        initBase();
    }

    public abstract void initView(View view);

    public abstract void initListener(View view);

    public abstract void initData();

    public abstract int getLayoutId();

    private void setNoTitle() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void initBase() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            window.setLayout((int) (displayMetrics.widthPixels * 0.8), WRAP_CONTENT);
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment != null) {
                transaction.remove(fragment);
            }
        }
        return super.show(transaction, tag);
    }
}
