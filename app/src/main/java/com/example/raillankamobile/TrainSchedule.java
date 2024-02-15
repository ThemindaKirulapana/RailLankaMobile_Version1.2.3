package com.example.raillankamobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrainSchedule extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter adapter;
    ArrayList<TrainTimeShedule> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedule);

        recyclerView=findViewById(R.id.TrainSheduleLis);
        database= FirebaseDatabase.getInstance().getReference("schedule");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        adapter=new Adapter(this,list);
        recyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot  dataSnapshot: snapshot.getChildren()) {
                    // Retrieve schedule information
                    String arrivalTime = snapshot.child("arrivalTime").getValue(String.class);
                    int delays = snapshot.child("delays").getValue(Integer.class);
                    String departureTime = snapshot.child("departureTime").getValue(String.class);
                    String finalDestination = snapshot.child("finalDestination").getValue(String.class);




                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}