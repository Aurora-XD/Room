package com.example.room.repository;

import com.example.room.model.User;

import io.reactivex.Maybe;

public interface UserDataSource {
    Maybe<User> findByName(String userId);
    Maybe<Long> saveUser(User user);
}
