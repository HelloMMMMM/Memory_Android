package com.hellom.memory;

import android.app.Application;

import com.didichuxing.doraemonkit.DoraemonKit;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DoraemonKit.install(this);
    }
}
