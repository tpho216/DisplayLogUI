package com.example.displaylogui;

import android.app.Application;

import com.example.displaylogui.controller.UILogger;

public class DisplayLogUIApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UILogger.init(this);
    }
}
