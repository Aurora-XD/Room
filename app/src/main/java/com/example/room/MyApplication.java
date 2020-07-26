package com.example.room;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class MyApplication extends Application {

    private static Context context;
    private static LocalDataSource localDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        localDataSource = Room.databaseBuilder(context,LocalDataSource.class,"database-room").build();
    }

    public static Context getContext() {
        return context;
    }

    public static LocalDataSource getLocalDataSource() {
        return localDataSource;
    }
}
