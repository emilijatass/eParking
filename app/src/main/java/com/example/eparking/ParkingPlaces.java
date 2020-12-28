package com.example.eparking;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class ParkingPlaces extends AppCompatActivity {

    String selectedCity;
    String selectedTimeSlot;
    String selectedDate;
    int selectedDay;
    int selectedMonth;
    int selectedYear;
    String name;
    public String username;

    TextView message;
    RecyclerView mRecyclerView;
    myAdapterParkingPlaces mAdapter;
    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_places);

        Intent intent = getIntent();
        selectedCity = intent.getStringExtra("selectedCity");
        selectedTimeSlot = intent.getStringExtra("selectedTimeSlot");
        selectedDate = intent.getStringExtra("selectedDate");
        selectedDay = intent.getIntExtra("selectedDay", 0);
        selectedMonth = intent.getIntExtra("selectedMonth", 0);
        selectedYear = intent.getIntExtra("selectedYear", 0);
        name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");

        message = (TextView) findViewById(R.id.parkingplacestext);
        message.setText("Слободни места во:" + selectedCity);

        database = new DBHelper(this);
        List<String> valuesNames = database.getParkings(selectedCity);

        mRecyclerView = (RecyclerView) findViewById(R.id.parkingplaceslist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new myAdapterParkingPlaces(valuesNames, R.layout.activity_my_adapter_parking_places, selectedDate, selectedTimeSlot, username, name, database, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, MyReservations.class);
        intent.putExtra("Username", username);
        intent.putExtra("Name", name);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}
