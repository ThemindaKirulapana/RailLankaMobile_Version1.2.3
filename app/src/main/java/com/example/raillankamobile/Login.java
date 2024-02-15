package com.example.raillankamobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Login extends AppCompatActivity {
    private EditText txtEditEmail, txtEditPassword;
    private Button btnLetMeIn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = firebaseDatabase.getReference();

        txtEditEmail = findViewById(R.id.txtEditus);
        txtEditPassword = findViewById(R.id.txtEditpw);
        btnLetMeIn = findViewById(R.id.btnLetMeIn);

        btnLetMeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = txtEditEmail.getText().toString();
        String password = txtEditPassword.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                String userId = currentUser.getUid();

                                // Query the database for user details
                                DatabaseReference userRef = mDatabase.child("customer").child(userId);
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            // User exists in the database, login successful
                                            // Proceed to your desired activity
                                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent login=new Intent(Login.this,dashbord.class);
                                            startActivity(login);
                                        } else {
                                            // User does not exist in the database
                                            Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Login.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(Login.this, "Current user is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("customer");
        Query checkUserDatabase=reference.orderByChild("userName").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    txtEditEmail.setError(null);
                    String ps=snapshot.child(username).child("password").getValue(String.class);


                    if(!Objects.equals(ps,password)){
                        txtEditEmail.setError(null);
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent login=new Intent(Login.this,dashbord.class);
                        startActivity(login);
                    }
                    else {
                        // User does not exist in the database
                        Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
