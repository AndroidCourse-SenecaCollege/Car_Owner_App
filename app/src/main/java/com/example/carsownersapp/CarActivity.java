package com.example.carsownersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity implements
        CarsAdapter.AlertDialogListner,
        AddNewCarFragment.AddCarFragmentListener,
        OwnerCarDBService.DBCallBackInterface
{

RecyclerView carlist;
ArrayList<Car> carArrayList;
CarsAdapter adapter;
OwnerCarDBService dbService;
OwnerCars OwnersCarsObject;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        id = getIntent().getExtras().getInt("ownerid");
        String name = getIntent().getExtras().getString("ownername");
        this.setTitle(name + "'s Cars");
        dbService = ((MyApp)getApplication()).dbService;
        dbService.listener = this;
        dbService.getAllCarsForOwner(id);

        //carArrayList = ((MyApp)getApplication()).getAllCarsForOneOwner(id);
        adapter = new CarsAdapter(this,new ArrayList<>(0));
       // adapter.carList = carArrayList;
        carlist = findViewById(R.id.carlist);
        carlist.setAdapter(adapter);
        carlist.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(carlist);


        FloatingActionButton fab = findViewById(R.id.addCarFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNewCarFragment fragment = AddNewCarFragment.buildFragment("Enter New Car As A gift");

               fragment.show(getSupportFragmentManager().beginTransaction(),"1");
                fragment.listener = CarActivity.this;
            }
        });
       }

    @Override
    public void CarAdapterClickListener(Car selectedCar) {
        Toast.makeText(this,"Selected Car is "+ selectedCar.model,Toast.LENGTH_LONG).show();
    }

    @Override
    public void addNewCar(String carModel, int year) {
        dbService.insertNewCar(carModel,year,id);
       // carArrayList.add(new Car(year,carModel,id));
    }








    // table view deleget
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(CarActivity.this, "Item Moveing", Toast.LENGTH_SHORT).show();

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            dbService.deleteCar(adapter.carList.get(position));
            //dbService.getAllCarsForOwner(id);
            //carArrayList.remove(position);


        }
    };

    @Override
    public void OwnerInserted() {

    }

    @Override
    public void listOfOwnersFormDB(List<Owner> list) {

    }

    @Override
    public void OwnerDeleted() {

    }

    @Override
    public void CarInserted() {
        // the car inserted correctly
        dbService.getAllCarsForOwner(id);
    }

    @Override
    public void listOfCarsForSelecteOwner(List<Car> list) {
        adapter.carList = list;
        adapter.notifyDataSetChanged();

    }

    @Override
    public void listOfCarsFormDB(List<Car> list) {

    }

    @Override
    public void CarDeleted() {
        dbService.getAllCarsForOwner(id);
    }
}