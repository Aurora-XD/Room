package com.example.room;

import android.util.Log;

import io.reactivex.Maybe;

public class UserRepository {
    private UserDBDatasource dbDatasource = new UserDBDatasource();

    public Maybe<User> findByUserName(String name){
        return dbDatasource.findByName(name);
    }

    public Maybe<Long> saveUser(User user){
        Log.d("UserRepository", "saveUser: ");
        return dbDatasource.saveUser(user);
    }
}
