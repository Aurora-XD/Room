package com.example.room;

import android.util.Log;

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
