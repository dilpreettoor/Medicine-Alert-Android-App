package com.example.malert;

import android.app.Application;

import com.example.malert.helper.MyDatabaseAdapter;

public class MyApplication extends Application {

    public static MyDatabaseAdapter myDatabaseAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        myDatabaseAdapter = new MyDatabaseAdapter(getApplicationContext());
    }
}
