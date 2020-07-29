package com.example.room.repository;

import android.util.Log;

import com.example.room.MyApplication;
import com.example.room.model.User;

import io.reactivex.Maybe;

public class UserDBDatasource {
    private UserDatabase userDatabase;

    public UserDBDatasource() {
        this.userDatabase = MyApplication.getUserDatabase();
    }

    public Maybe<User> findByName(String name){
        return userDatabase.getUserDao().getUserByName(name);
    }

    public Maybe<Long> saveUser(User user){
        Log.d("UserDBDatasource", "saveUser: ");
        return userDatabase.getUserDao().insertUser(user);
    }
}
