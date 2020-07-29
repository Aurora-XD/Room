package com.example.room;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.room.repository.UserDatabase;
import com.example.room.repository.UserRepository;

public class MyApplication extends Application {

    private static Context context;
    private static LocalDataSource localDataSource;
    private static UserRepository userRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        localDataSource = Room.databaseBuilder(context,LocalDataSource.class,"database-room").build();

        UserDatabase userDatabase = Room.databaseBuilder(context,UserDatabase.class,"database-user").allowMainThreadQueries().build();
        userRepository = new UserRepository(userDatabase.getUserDao());
    }

    public static Context getContext() {
        return context;
    }

    public static LocalDataSource getLocalDataSource() {
        return localDataSource;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }
}
