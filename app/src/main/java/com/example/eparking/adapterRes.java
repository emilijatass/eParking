package com.example.eparking;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class adapterRes extends  RecyclerView.Adapter<adapterRes.ReservationsViewHolder> {
    private List<List<String>> myList;
    private int rowLayout;
    private Context mContext;
    private String username;
    private String name;
    private DBHelper database;

    public class ReservationsViewHolder extends RecyclerView.ViewHolder {
        public TextView parkingName;
        public TextView cityName;
        public TextView dateTime;
        public Button details;

        public ReservationsViewHolder(View itemView) {
            super(itemView);
            parkingName = (TextView) itemView.findViewById(R.id.parkingnamereservations);
            cityName = (TextView) itemView.findViewById(R.id.citynamereservations);
            dateTime = (TextView) itemView.findViewById(R.id.dateandtime);

        }
    }

    public adapterRes(List<List<String>> myList, int rowLayout, String username, String name, DBHelper database, Context context) {
        this.myList = myList;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.username = username;
        this.database = database;
        this.name = name;
    }

    public ReservationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ReservationsViewHolder(v);
    }

    public void onBindViewHolder(ReservationsViewHolder viewHolder, int i) {
        final List<String> entry = myList.get(i);
        final float lat = database.getLat(entry.get(0));
        final float lng = database.getLong(entry.get(0));
        viewHolder.parkingName.setText(entry.get(0));
        viewHolder.dateTime.setText(entry.get(1) + "\n" + entry.get(2));
        viewHolder.cityName.setText(database.getCity(entry.get(0)));
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Confirmation.class);
                intent.putExtra("Ime", entry.get(0));
                intent.putExtra("Korisnichko", username);
                intent.putExtra("Datum", entry.get(1));
                intent.putExtra("Vreme", entry.get(2));
                intent.putExtra("Lat", lat);
                intent.putExtra("Long", lng);
                intent.putExtra("Name", name);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

}