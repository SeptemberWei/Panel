package com.foton.library;

import android.app.Application;

import com.foton.library.callback.AppLifecycleCallback;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new AppLifecycleCallback());
    }
}
