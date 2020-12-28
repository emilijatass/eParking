package com.example.eparking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "MyDatabase", null, 9);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ParkingPlaces(name VARCHAR, city VARCHAR, totalSpaces INTEGER, lat FLOAT, long FLOAT);");
        db.execSQL("CREATE TABLE Reservations(username VARCHAR, parkingname VARCHAR, date VARCHAR, timeslot VARCHAR);");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ;");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ParkingPlaces");
        db.execSQL("DROP TABLE IF EXISTS Reservations");
        db.execSQL("CREATE TABLE ParkingPlaces(name VARCHAR, city VARCHAR, totalSpaces INTEGER, lat FLOAT, long FLOAT);");
        db.execSQL("CREATE TABLE Reservations(username VARCHAR, parkingname VARCHAR, date VARCHAR, timeslot VARCHAR);");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES");
        db.execSQL("INSERT INTO ParkingPlaces VALUES");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
        db.execSQL("INSERT INTO ParkingPlaces VALUES");
        db.execSQL("INSERT INTO ParkingPlaces VALUES ");
    }

    public void insertIntoReservations(String username, String parkingname, String date, String timeslot) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("parkingname", parkingname);
        contentValues.put("username", username);
        contentValues.put("date", date);
        contentValues.put("timeslot", timeslot);
        database.insert("Reservations", null, contentValues);
    }

    public List<String> getParkings(String city) {
        List<String> parkingNames = new ArrayList();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM ParkingPlaces WHERE city LIKE '" + city + "'", null);
        while (c1.moveToNext()) {
            parkingNames.add(c1.getString(0));
        }
        c1.close();
        return parkingNames;
    }

    public int getTotalSpaces(String name) {
        int total = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM ParkingPlaces WHERE name LIKE '" + name + "'", null);
        if (c1.moveToFirst()) {
            total = c1.getInt(2);
            c1.close();
            return total;
        } else {
            return 0;
        }
    }

    public int getNumberOfReservations(String date, String time, String parkingname) {
        int count = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM Reservations WHERE date = '" + date + "' AND timeslot = '" + time + "' AND parkingname = '" + parkingname + "'", null);
        if (c1.moveToFirst()) {
            count = c1.getCount();
            c1.close();
            return count;
        } else {
            return 0;
        }
    }

    public boolean existingReservation(String username, String date, String timeslot) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM Reservations WHERE date = '" + date + "' AND timeslot = '" + timeslot + "' AND username = '" + username + "'", null);
        if (c1.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public float getLat(String parkingname) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM ParkingPlaces WHERE name LIKE '" + parkingname + "'", null);
        if (c1.moveToFirst()) {
            return c1.getFloat(3);
        } else {
            return 0;
        }
    }

    public float getLong(String parkingname) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM ParkingPlaces WHERE name LIKE '" + parkingname + "'", null);
        if (c1.moveToFirst()) {
            return c1.getFloat(4);
        } else {
            return 0;
        }
    }

    public String getCity(String parkingname) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM ParkingPlaces WHERE name LIKE '" + parkingname + "'", null);
        if (c1.moveToFirst()) {
            return c1.getString(1);
        } else {
            return "";
        }
    }

    public boolean hasThreeActiveReservations(String username) {
        SQLiteDatabase database = this.getReadableDatabase();
        int counter = 0;
        Cursor c1 = database.rawQuery("SELECT * FROM Reservations WHERE username = '" + username + "'", null);
        while (c1.moveToNext()) {
            String date = c1.getString(2);
            String time = c1.getString(3);
            if (isActive(date, time)) {
                counter++;
            }
        }
        c1.close();
        if (counter < 3) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isActive(String datum, String vreme) {
        String[] date = datum.split("/");
        int rDay = Integer.parseInt(date[0]);
        int rMonth = Integer.parseInt(date[1]);
        int rYear = Integer.parseInt(date[2]);
        String time = vreme.substring(0, 2);
        int rHour = Integer.parseInt(time);

        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int year = rightNow.get(Calendar.YEAR);
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        if (year > rYear) {
            return false;
        } else if (year < rYear) {
            return true;
        } else {
            if (month > rMonth) {
                return false;
            } else if (month < rMonth) {
                return true;
            } else {
                if (day > rDay) {
                    return false;
                } else if (day < rDay) {
                    return true;
                } else {
                    if (hour > rHour) {
                        return false;
                    } else if (hour < rHour) {
                        return true;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    public List<List<String>> getReservations(String username) {
        List<List<String>> reservations = new ArrayList<List<String>>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c1 = database.rawQuery("SELECT * FROM Reservations WHERE username LIKE '" + username + "'", null);
        while (c1.moveToNext()) {
            List<String> row = new ArrayList();
            String date = c1.getString(2);
            String time = c1.getString(3);
            if (this.isActive(date, time)) {
                row.add(c1.getString(1));
                row.add(date);
                row.add(time);
                reservations.add(row);
            }
        }
        c1.close();
        return reservations;
    }
}
