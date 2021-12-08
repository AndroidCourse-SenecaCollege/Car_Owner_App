package com.example.carsownersapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Car {


    // (2021, Nissan) == (29304949 , 2021, Nissan)
    // (2000 , BMW) === (34848373 , 2000, BMW)
    //
    @PrimaryKey (autoGenerate = true)
    int id;
    int year;
    String model;

    int ownerID;//


    public Car( int year, String model, int ownerID) {

        this.year = year;
        this.model = model;
        this.ownerID = ownerID;
    }
}
