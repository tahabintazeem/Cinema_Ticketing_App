package com.example.cinemaapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cinemaapp.R;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsernameSignup, editTextPasswordSignup, editTextEmailSignup;
    Button buttonSignupConfirm;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsernameSignup = findViewById(R.id.editTextUsernameSignup);
        editTextPasswordSignup = findViewById(R.id.editTextPasswordSignup);
        editTextEmailSignup = findViewById(R.id.editTextEmailSignup);
        buttonSignupConfirm = findViewById(R.id.buttonSignupConfirm);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        buttonSignupConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsernameSignup.getText().toString();
                String password = editTextPasswordSignup.getText().toString();
                String email = editTextEmailSignup.getText().toString();

                // Basic input validation (you should add more robust validation)
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add user to the database
                boolean isUserCreated = dbHelper.addUser(username, password);
                if (isUserCreated) {
                    // User created successfully
                    Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    // Navigate back to the login screen
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // User creation failed (likely due to duplicate username)
                    Toast.makeText(SignupActivity.this, "User creation failed. Username may already exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("kya aap waqai humy chor kr jana chahte hain?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SignupActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}