package com.example.room;

import android.app.Application;
import android.content.Context;

import com.example.room.repository.UserDatabase;

import static org.mockito.Mockito.mock;

public class MyApplication extends Application {
    private static Context context;
    private static UserDatabase userDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        if(context == null){
            mock(Application.class);
        }
        return context;
    }

    public static UserDatabase getUserDatabase() {
        if(userDatabase == null){
            mock(UserDatabase.class);
        }
        return userDatabase;
    }
}
