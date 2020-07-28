package com.example.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Maybe;

@Dao
public interface UserDao {

    @Insert
    Maybe<Long> insertUser(User user);

    @Query("select * from user where username = :name")
    Maybe<User> getUserByName(String name);

}
