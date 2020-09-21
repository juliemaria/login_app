package com.example.ecobeeloginapp;

import android.app.Application;
import android.content.Context;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ApplicationController extends Application {
    private static Context context;
    private static Scheduler scheduler;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }


    public static Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        ApplicationController.scheduler = scheduler;
    }
}

