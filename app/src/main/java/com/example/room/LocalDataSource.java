package com.example.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {},version = 1)
public abstract class LocalDataSource extends RoomDatabase {
    public abstract PersonDao getPersonDao();
}
