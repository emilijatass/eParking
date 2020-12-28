package com.example.eparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class MyReservations extends AppCompatActivity {
    String username;
    String name;
    RecyclerView mRecyclerView;
    adapterRes mAdapter;
    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        name = intent.getStringExtra("Name");
        database = new DBHelper(this);
        List<List<String>> values = database.getReservations(username);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewreservations);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new adapterRes(values, R.layout.adapter_res, username, name, database, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}