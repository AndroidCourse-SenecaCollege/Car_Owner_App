package com.example.carsownersapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddNewCarFragment extends DialogFragment {
    
    interface AddCarFragmentListener{
        void addNewCar(String carModel, int year);
    }

    AddCarFragmentListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_add_new_car, container, false);
        EditText model = v.findViewById(R.id.model_text);
        EditText year = v.findViewById(R.id.year_text);

        Button save = v.findViewById(R.id.save_id);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!model.getText().toString().isEmpty() && !year.getText().toString().isEmpty())  {
                    listener.addNewCar(model.getText().toString(), Integer.parseInt(year.getText().toString()));
                    dismiss();
                }
            }
        });
        return v;
    }

}