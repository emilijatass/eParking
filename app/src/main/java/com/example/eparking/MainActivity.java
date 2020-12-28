package com.example.eparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    EditText input_username;
    EditText input_password;
    String name;
    String surname;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Users(name VARCHAR, surname VARCHAR, username VARCHAR, password VARCHAR);");
    }

    public void logIn(View view) {
        input_username = (EditText) findViewById(R.id.korime);
        input_password = (EditText) findViewById(R.id.lozinka);

        if (input_username.getText().toString().trim().length() == 0 || input_password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please fill in the blanks!", Toast.LENGTH_LONG).show();
        } else {
            Cursor c1 = database.rawQuery("SELECT * FROM Users WHERE username LIKE '" + input_username.getText().toString().trim() + "'", null);
            if (c1.moveToFirst()) {

                username = c1.getString(2);
                password = c1.getString(3);

                if (password.equals(input_password.getText().toString().trim())) {
                    name = c1.getString(0);
                    surname = c1.getString(1);
                    c1.close();
                    Intent intent = new Intent(this, Cities.class);
                    intent.putExtra("Ime", name);
                    intent.putExtra("KorisnichkoIme", username);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Incorrect password!", Toast.LENGTH_LONG).show();
                    input_username.setText("");
                    input_password.setText("");
                    c1.close();
                }
            } else {
                Toast.makeText(this, "Invalid username", Toast.LENGTH_LONG).show();
                input_username.setText("");
                input_password.setText("");
                c1.close();
            }
        }
    }

    public void signUp(View view) {

        Intent intent1 = new Intent(this, Registration.class);
        startActivity(intent1);
    }

}