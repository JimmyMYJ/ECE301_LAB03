package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seed data
        String[] cities    = {"Edmonton", "Vancouver", "Toronto", "Montreal"};
        String[] provinces = {"AB",       "BC",        "ON",      "QC"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add new city (FAB)
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                AddCityFragment.newInstance(null, -1)
                        .show(getSupportFragmentManager(), "ADD_CITY"));

        // Edit existing city on tap
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City clicked = (City) parent.getItemAtPosition(position);
            AddCityFragment.newInstance(clicked, position)
                    .show(getSupportFragmentManager(), "EDIT_CITY");
        });
    }

    // Fragment callbacks
    @Override
    public void addCity(City city) {
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(int position, City updatedCity) {
        dataList.set(position, updatedCity);      // replace with edited value
        cityAdapter.notifyDataSetChanged();
    }
}
