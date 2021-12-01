package com.rhythm.mypizzaapp.application;

import android.app.Application;

import com.rhythm.mypizzaapp.utils.TypefaceUtil;


public class MyApplication extends Application {
    private static MyApplication mInstance;
    private String mFcmToken;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        /* Override FontStyle from Assets */
        TypefaceUtil.getInstance().overrideFont(getApplicationContext(), "sans_serif", "fonts/rounded_elegance.ttf"); // font from assets: "assets/fonts/rounded_elegance.ttf
    }

    public static synchronized MyApplication getApplication() {
        return mInstance;
    }

}