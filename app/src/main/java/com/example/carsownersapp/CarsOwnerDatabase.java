package com.example.carsownersapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Car.class,Owner.class})
abstract class CarsOwnerDatabase extends RoomDatabase {
    public abstract DAO getDAO();
}
