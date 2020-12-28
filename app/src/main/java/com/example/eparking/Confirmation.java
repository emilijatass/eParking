package com.example.eparking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;

public class Confirmation extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fragment frag1 =  getFragmentManager().findFragmentById(R.id.fragment1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }
}