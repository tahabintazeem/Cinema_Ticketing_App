package com.example.cinemaapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.cinemaapp.R;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Find buttons
        Button manageMoviesBtn = findViewById(R.id.btn_manage_movies);
        Button manageTheatersBtn = findViewById(R.id.btn_manage_theaters);
        Button manageShowtimesBtn = findViewById(R.id.btn_manage_showtimes);
        Button manageUsersBtn = findViewById(R.id.btn_manage_users);

        // Set click listeners
        manageMoviesBtn.setOnClickListener(this);
        manageTheatersBtn.setOnClickListener(this);
        manageShowtimesBtn.setOnClickListener(this);
        manageUsersBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Display toast for each button clicked
        switch (v.getId()) {
            case R.id.btn_manage_movies:
                showToast("Manage Movies feature coming soon!");
                break;
            case R.id.btn_manage_theaters:
                showToast("Manage Theaters feature coming soon!");
                break;
            case R.id.btn_manage_showtimes:
                showToast("Manage Showtimes feature coming soon!");
                break;
            case R.id.btn_manage_users:
                showToast("Manage Users feature coming soon!");
                break;
        }
    }

    // Helper method to display toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
