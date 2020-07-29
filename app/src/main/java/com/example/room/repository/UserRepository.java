package com.example.room.repository;

import android.util.Log;

import com.example.room.model.User;

import io.reactivex.Maybe;

public class UserRepository {
    private UserDBDatasource dbDatasource;

    public UserRepository(UserDBDatasource dbDatasource) {
        this.dbDatasource = dbDatasource;
    }

    public Maybe<User> findByUserName(String name){
        return dbDatasource.findByName(name);
    }

    public Maybe<Long> saveUser(User user){
        Log.d("UserRepository", "saveUser: ");
        return dbDatasource.saveUser(user);
    }
}
