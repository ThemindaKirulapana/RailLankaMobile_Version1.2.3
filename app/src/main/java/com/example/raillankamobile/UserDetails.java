package com.example.raillankamobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetails extends AppCompatActivity {

    private TextView textViewNIC;
    private TextView textViewName;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textViewNIC = findViewById(R.id.textViewNIC);
        textViewName = findViewById(R.id.textViewName);
        //textViewBalance = findViewById(R.id.textViewBalance);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("customer").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String nic = dataSnapshot.child("NIC").getValue(String.class);
                        String name = dataSnapshot.child("userName").getValue(String.class);
                        //String balance = dataSnapshot.child("Balance").getValue(String.class);

                        textViewNIC.setText("NIC No: " + nic);
                        textViewName.setText("Name: " + name);
                        //textViewBalance.setText("Balance: " + balance);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
}
