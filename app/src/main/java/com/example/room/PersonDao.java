package com.example.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PersonDao {

    @Insert
    Single<Person> createPerson(Person person);

    @Query("select * from person order by id")
    Single<List<Person>> getAllPerson();
}
