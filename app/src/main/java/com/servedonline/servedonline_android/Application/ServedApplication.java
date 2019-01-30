package com.servedonline.servedonline_android.Application;

import android.app.Application;

import com.servedonline.servedonline_android.Database.Database;

public class ServedApplication extends Application {

    private Database database;

    private boolean isCurrentlyUploadingForms = false;

    @Override
    public void onCreate() {
        super.onCreate();

        database = new Database(this, getMainLooper());
    }

    public Database getDatabase() {
        return database;
    }

    public boolean isCurrentlyUploadingForms() {
        return isCurrentlyUploadingForms;
    }

    public void setCurrentlyUploadingForms(boolean currentlyUploadingForms) {
        isCurrentlyUploadingForms = currentlyUploadingForms;
    }
}
