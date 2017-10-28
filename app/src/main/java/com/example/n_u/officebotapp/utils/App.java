package com.example.n_u.officebotapp.utils;

import com.activeandroid.ActiveAndroid;

import io.fabric.sdk.android.Fabric;

/**
 * Created by usman on 13-Jun-17.
 */

public class App extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final io.fabric.sdk.android.Fabric fabric = new io.fabric.sdk.android.Fabric.Builder(this)
                .kits(new com.crashlytics.android.Crashlytics())
//                .kits(new com.crashlytics.android.answers.Answers())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        ActiveAndroid.initialize(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
