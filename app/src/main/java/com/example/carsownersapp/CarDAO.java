package com.example.carsownersapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CarDAO {
    @Insert
    void insertNewOwner(Owner o);

    @Insert
    void insertNewCar(Car c);


    @Delete
    void deleteCar(Car c);



    @Query("Select * from Owner")
    List<Owner> getAllOwners();


    @Query("Select * from Owner where owner_id = :id")
    Owner getOwnerWithID(int id);

    @Query("Select * from Car")
    List<Car> getAllCar();

    //


// Rania id 778
    // go to Car enity : give me all cars that their ownerid = 778
    // OwnerCar {
    // Rania 778
    // list of all cars that Rania ownes.
    // }

    @Transaction
    @Query("select * from Owner where owner_id = :iD")
    OwnerCar getAllCarsForOwner(int iD);

//
//    @Transaction
//    @Query("delete from Owner where owner_id = :id")
//    void deleteAllCarsAndOwner(int id);



    @Query("delete from Car where ownerID = :id")
    void deleteAllCarsForOwner(int id);

    @Delete
    void deleteOwner(Owner o);


}