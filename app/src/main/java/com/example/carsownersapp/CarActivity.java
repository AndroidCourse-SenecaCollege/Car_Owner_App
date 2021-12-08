package com.example.carsownersapp;

import androidx.appcompat.app.AppCompatActivity;
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
        CarsAdapter.AlertDialogListner, AddNewCarFragment.AddCarFragmentListener,
        OwnerCarsDatabaseService.DatabaseListener
{

    OwnerCarsDatabaseService dbService = new OwnerCarsDatabaseService();
RecyclerView carlist;
CarsAdapter adapter;
int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        id = getIntent().getExtras().getInt("ownerid");
        String name = getIntent().getExtras().getString("ownername");
        this.setTitle(name + "'s Cars");

        dbService.listener = this;
        dbService.getDbInstance(this);
        dbService.getAllCarsForOwner(id);

        adapter = new CarsAdapter(this,new ArrayList<>(0));
        carlist = findViewById(R.id.carlist);
        carlist.setAdapter(adapter);
        carlist.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.addCarFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewCarFragment fragment = new AddNewCarFragment();
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
    }

    @Override
    public void getAllOwnersListener(List<Owner> list) {

    }

    @Override
    public void insertOwnerListener() {

    }

    @Override
    public void getAllCarsForOwnerListener(OwnerCars obj) {
        adapter.carList = obj.cars;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void insertNewCarListener() {
        dbService.getAllCarsForOwner(id);

    }
}