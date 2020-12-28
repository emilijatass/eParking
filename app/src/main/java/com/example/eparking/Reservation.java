package com.example.eparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Reservation extends AppCompatActivity {

    DBHelper database;
    TextView reservationMessage;
    Spinner timeSlots;
    DatePicker datePicker;

    String selectedCity;
    String selectedTimeSlot;
    String selectedDate;
    int selectedDay;
    int selectedMonth;
    int selectedYear;
    String name;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Fragment frag1 =  getFragmentManager().findFragmentById(R.id.fragment1);
        Fragment frag2 =  getFragmentManager().findFragmentById(R.id.fragment2);

        Intent intent = getIntent();
        selectedCity = intent.getStringExtra("ImeGrad");
        name = intent.getStringExtra("Ime");
        username = intent.getStringExtra("Korisnichko");

        timeSlots = (Spinner) findViewById(R.id.timeslots);
        reservationMessage = (TextView) findViewById(R.id.reservationmessage);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        database = new DBHelper(this);

        reservationMessage.setText( "You made your reservation in" + "  " + selectedCity + "!");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent (this, MyReservations.class);
        intent.putExtra("Username", username);
        intent.putExtra("Name", name);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void event(View view) {
        selectedTimeSlot = timeSlots.getSelectedItem().toString();
        selectedDay = datePicker.getDayOfMonth();
        selectedMonth = datePicker.getMonth() + 1;
        selectedYear = datePicker.getYear();
        selectedDate = selectedDay + "/" + selectedMonth + "/" + selectedYear;
        if(database.hasThreeActiveReservations(username)) {
            Toast.makeText(this, "You have 3 active reservations!", Toast.LENGTH_LONG).show();
        } else if(database.existingReservation(username, selectedDate, selectedTimeSlot)) {
            Toast.makeText(this, "It already exists reservation in the selected time!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, ParkingPlaces.class);
            intent.putExtra("selectedTimeSlot", selectedTimeSlot);
            intent.putExtra("selectedDay", selectedDay);
            intent.putExtra("selectedMonth", selectedMonth);
            intent.putExtra("selectedYear", selectedYear);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedCity", selectedCity);
            intent.putExtra("name", name);
            intent.putExtra("username", username);
            startActivity(intent);
        }

    }
}