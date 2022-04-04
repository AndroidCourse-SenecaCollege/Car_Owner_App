package com.example.carsownersapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerCarDBService {

    interface DBCallBackInterface{
        void OwnerInserted();
        void listOfOwnersFormDB(List<Owner> list);
        void OwnerDeleted();
    }

    OwnerCarsDB db;
    DBCallBackInterface listener;
    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

   public OwnerCarsDB getInstance(Context context){
       if (db == null) {
           db = Room.databaseBuilder(context.getApplicationContext(),
                   OwnerCarsDB.class, "owners_cars_db").build();
       }
       return db;
   }

   public void insertNewOwner(String  name){
       dbExecutor.execute(new Runnable() {
           @Override
           public void run() {
               db.getDao().insertNewOwner(new Owner(name));
               dbHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       listener.OwnerInserted();
                   }
               });

           }
       });
   }

   public void getAllOwners(){
       dbExecutor.execute(new Runnable() {
           @Override
           public void run() {
               List<Owner> list = db.getDao().getAllOwners();
               dbHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       listener.listOfOwnersFormDB(list);
                   }
               });

           }
       });
   }

public void deleteOwnerAndCars(Owner todelete){
    dbExecutor.execute(new Runnable() {
        @Override
        public void run() {
           db.getDao().deleteAllCarsForOwner(todelete.owner_id);
           db.getDao().deleteOwner(todelete);
            dbHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.OwnerDeleted();
                }
            });

        }
    });
}

}
