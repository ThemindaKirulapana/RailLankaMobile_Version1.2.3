package com.example.raillankamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dashbord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        Button s = findViewById(R.id.search_Train);
         s .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the other activity
                Intent intent = new Intent(dashbord.this, shedule.class);
                startActivity(intent);
            }
        });

        Button t = findViewById(R.id.tour_Info);
        t .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the other activity
                Intent intent = new Intent(dashbord.this, UserDetails.class);
                startActivity(intent);
            }
        });
    }
}