package com.example.room.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.room.model.User;

@Database(entities = {User.class},version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
