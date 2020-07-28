package com.example.room;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class MyApplication extends Application {

    private static Context context;
    private static LocalDataSource localDataSource;

    private static UserDatabase userDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        localDataSource = Room.databaseBuilder(context,LocalDataSource.class,"database-room").build();

        userDatabase = Room.databaseBuilder(context,UserDatabase.class,"database-user").allowMainThreadQueries().build();
    }

    public static Context getContext() {
        return context;
    }

    public static LocalDataSource getLocalDataSource() {
        return localDataSource;
    }

    public static UserDatabase getUserDatabase() {
        return userDatabase;
    }
}
