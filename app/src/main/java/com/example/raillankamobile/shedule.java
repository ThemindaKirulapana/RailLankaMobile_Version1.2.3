package com.example.raillankamobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class shedule extends AppCompatActivity {

    private DatabaseReference database;
    private EditText startStationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().getReference();


        // Find views
        startStationEditText = findViewById(R.id.startStation);
        Button searchButton = findViewById(R.id.SearchShedule);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the start station input
                String startStation = startStationEditText.getText().toString().trim();

                // Perform the search
                searchTrainScheduleByStation(startStation);
            }
        });

        Button searchButton1 = findViewById(R.id.search_allShedule);
        searchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shedule.this, TrainSchedule.class);
                startActivity(intent);
            }
        });
    }

    private void searchTrainScheduleByStation(String startStation) {

        // Query to fetch train schedules based on the start station
         Query query = database.child("schedule").orderByChild("City").equalTo(startStation);
        //Query query = database.child("schedule").orderByChild("railwayStation/city").equalTo(startCity);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Process the retrieved data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve schedule information
                    String arrivalTime = snapshot.child("arrivalTime").getValue(String.class);
                    int delays = snapshot.child("delays").getValue(Integer.class);
                    String departureTime = snapshot.child("departureTime").getValue(String.class);
                    String finalDestination = snapshot.child("finalDestination").getValue(String.class);

                    // Display the information
                    displaySchedule(arrivalTime, delays, departureTime, finalDestination);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });

    }

    private void displaySchedule(String arrivalTime, int delays, String departureTime, String finalDestination) {

        // Update UI to display the schedule information
        TextView arrivalTextView = findViewById(R.id.arrivalTimeTextView);
        TextView delaysTextView = findViewById(R.id.delaysTextView);
        TextView departureTextView = findViewById(R.id.departureTimeTextView);
        TextView destinationTextView = findViewById(R.id.destinationTextView);

        arrivalTextView.setText("Arrival Time: " + arrivalTime);
        delaysTextView.setText("Delays: " + delays + " minutes");
        departureTextView.setText("Departure Time: " + departureTime);
        destinationTextView.setText("Final Destination: " + finalDestination);


    }



}