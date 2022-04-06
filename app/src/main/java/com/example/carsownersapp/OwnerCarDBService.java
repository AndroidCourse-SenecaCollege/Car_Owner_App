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
        void CarInserted();
        void listOfCarsForSelecteOwner(List< Car> list);
        void listOfCarsFormDB(List< Car> list);
        void CarDeleted();
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

    public void getAllCarsForOwner(int  oid){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OwnerCar ownerCar = db.getDao().getAllCarsForOwner(oid);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listOfCarsForSelecteOwner(ownerCar.carList);
                    }
                });

            }
        });
    }

    public void insertNewCar(String  model, int year, int oID){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().insertNewCar(new Car(year,model,oID));
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.CarInserted();
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


    public void getAllCars(){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Car> list = db.getDao().getAllCar();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listOfCarsFormDB(list);
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

           // db.getDao().deleteAllCarsAndOwner(todelete.owner_id);
            dbHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.OwnerDeleted();
                }
            });

        }
    });
}


    public void deleteCar(Car todelete){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteCar(todelete);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.CarDeleted();
                    }
                });

            }
        });
    }

}
