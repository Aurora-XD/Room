package com.example.room.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.room.model.User;

import io.reactivex.Maybe;

@Dao
public interface UserDBDatasource extends UserDataSource {
    @Insert
    public Maybe<Long> saveUser(User user);

    @Query("select * from user where username = :name")
    public Maybe<User> findByName(String name);
}
