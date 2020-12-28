package com.example.eparking;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    SQLiteDatabase database;
    EditText input_name;
    EditText input_surname;
    EditText input_username;
    EditText input_password;
    String name;
    String surname;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        database = openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
    }

    public void addUser(View view) {
        input_name = (EditText) findViewById(R.id.registracija_ime);
        input_surname = (EditText) findViewById(R.id.registracija_prezime);
        input_username = (EditText) findViewById(R.id.registracija_korime);
        input_password = (EditText) findViewById(R.id.registracija_lozinka);

        if (input_name.getText().toString().trim().length() == 0 || input_surname.getText().toString().trim().length() == 0 || input_username.getText().toString().trim().length() == 0 || input_password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please fill in the blanks!", Toast.LENGTH_LONG).show();
        } else {
            name = input_name.getText().toString().trim();
            surname = input_surname.getText().toString().trim();
            username = input_username.getText().toString().trim();
            password = input_password.getText().toString().trim();

            Cursor c1 = database.rawQuery("SELECT * FROM Users WHERE username LIKE '" + username + "'", null);
            if (c1.moveToFirst()) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
                c1.close();
                input_username.setText("");
            }
            else {
                database.execSQL("INSERT INTO Users VALUES('" + name + "', '" + surname + "', '" + username + "', '" + password + "' );");
                Toast.makeText(this, "User added successfully!", Toast.LENGTH_LONG).show();
                c1.close();
            }

        }
    }
}