package com.example.carsownersapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DAO {


    @Insert
    void insertNewCar(Car c);

    @Insert
    void insertNewOwner(Owner d);

    @Query("SELECT * FROM Owner")
    List<Owner> getAll();

    @Query("SELECT * FROM Car")
    List<Car> getAllcars();


    // Owner Table 837382 - Rania
    // Car Table
    // 444 - 2021 - Nissan - 837382
    /// 333 - 2000 - BMW - 837382

    // SELECT * FROM OWNER WHERE OWNERID = 837382
    // { RANIA , [444 - 2021 - Nissan - 837382, 333 - 2000 - BMW - 837382]}
    //join

    @Transaction
    @Query("SELECT * FROM OWNER WHERE owner_id = :id ")
    OwnerCars getAllCarsForOwner(int id);


    @Delete
    void deleteCar(Car c);


    @Transaction
    @Query("Delete FROM Car where ownerID = :id")
    void deleteAllCarsForOwner(int id);


    @Delete
    void DeleteOwner(Owner o);



}
