package com.example.carsownersapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerCarsDatabaseService {

    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

    CarsOwnerDatabase dbInstance;


    interface DatabaseListener{
        void getAllOwnersListener(List<Owner> list);
        void insertOwnerListener();
        void getAllCarsForOwnerListener(OwnerCars obj);
        void insertNewCarListener();
    }

    DatabaseListener listener;

    CarsOwnerDatabase getDbInstance(Context context){

        if (dbInstance == null)
            dbInstance =  Room.databaseBuilder(context.getApplicationContext(),
                    CarsOwnerDatabase.class, "database-owners-cars").build();
            return dbInstance;
    }


    public void getAllOwners(){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
              List<Owner> list =   dbInstance.getDAO().getAll();
              dbHandler.post(new Runnable() {
                  @Override
                  public void run() {
                      listener.getAllOwnersListener(list);
                  }
              });
            }
        });
    }

    public void insertNewOwner(String name){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbInstance.getDAO().insertNewOwner(new Owner(name));
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.insertOwnerListener();
                    }
                });
            }
        });
    }


    public void insertNewCar(String model,int year, int ownerID){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbInstance.getDAO().insertNewCar(new Car(year,model,ownerID));
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.insertNewCarListener();
                    }
                });
            }
        });
    }



    public void getAllCarsForOwner(int ownerID){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OwnerCars obj = dbInstance.getDAO().getAllCarsForOwner(ownerID);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.getAllCarsForOwnerListener(obj);
                    }
                });
            }
        });
    }

}
