package com.hellom.memory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hellom.memory.photo.view.PhotoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    private void checkPermission() {
        if (PermissionUtils.isGranted(PermissionConstants.STORAGE)) {
            loadFragment();
        } else {
            PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    loadFragment();
                }

                @Override
                public void onDenied() {
                    ToastUtils.showShort(getString(R.string.permission_denied));
                }
            }).request();
        }
    }

    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, PhotoFragment.newInstance());
        transaction.commit();
    }
}
