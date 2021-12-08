package com.example.carsownersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddOwnerFragment.AddOwnerFragmentListener,
        OwnerAdapter.AdapterListner,
        OwnerCarsDatabaseService.DatabaseListener
{
RecyclerView ownerList;
    OwnerAdapter adapter;
    OwnerCarsDatabaseService dbService = new OwnerCarsDatabaseService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbService.getDbInstance(this);
        dbService.listener = this;
        dbService.getAllOwners();

        ownerList = findViewById(R.id.ownerlist);
        ownerList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OwnerAdapter(this,new ArrayList<>(0));
        ownerList.setAdapter(adapter);
        adapter.listner = this;

        FloatingActionButton fab = findViewById(R.id.addOwnerFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOwnerFragment fragment = new AddOwnerFragment();
                fragment.show(getSupportFragmentManager().beginTransaction(),"1");
                fragment.listener = MainActivity.this;
            }
        });


    }



    @Override
    public void OwnerAdapterClickListener(Owner selectedOwner) {
        Intent intent = new Intent(this,CarActivity.class);
        intent.putExtra("ownerid",selectedOwner.owner_id);
        intent.putExtra("ownername",selectedOwner.name);

        startActivity(intent);
    }


    @Override
    public void addNewOwner(String ownerName) {
        dbService.insertNewOwner(ownerName);

    }

    @Override
    public void getAllOwnersListener(List<Owner> list) {
        adapter.ownerList = list;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void insertOwnerListener() {
        dbService.getAllOwners();
    }

    @Override
    public void getAllCarsForOwnerListener(OwnerCars obj) {

    }

    @Override
    public void insertNewCarListener() {

    }
}