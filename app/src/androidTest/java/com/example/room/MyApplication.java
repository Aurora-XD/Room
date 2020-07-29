package com.example.room;

import android.app.Application;
import android.content.Context;

import com.example.room.repository.UserRepository;

import static org.mockito.Mockito.mock;

public class MyApplication extends Application {
    private UserRepository userRepository;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        if (context == null) {
            context = mock(Application.class);
        }
        return context;
    }

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = mock(UserRepository.class);
        }
        return userRepository;
    }
}
