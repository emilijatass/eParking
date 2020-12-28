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

import java.util.Arrays;
import java.util.List;

public class Cities extends AppCompatActivity {

    public String name;
    public String username;


    RecyclerView mRecyclerView;
    myAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Intent intent = getIntent();
        name = intent.getStringExtra("Ime");
        username = intent.getStringExtra("KorisnichkoIme");


        List<String> values = Arrays.asList("Skopje", "Veles", "Kochani",
                "Shtip", "Ohrid", "Struga", "Prilep", "Kumanovo",
                "Tetovo", "Gostivar");

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new myAdapter(values, R.layout.myrow, name, username,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
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
}