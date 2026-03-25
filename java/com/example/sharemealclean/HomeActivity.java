package com.example.sharemealclean;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    CardView cardDonate, cardNgo, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize CardViews
        cardDonate = findViewById(R.id.cardDonate);
        cardNgo = findViewById(R.id.cardNgo);
        cardLogout = findViewById(R.id.cardLogout);

        // Donate Food Click
        cardDonate.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DonateActivity.class);
            startActivity(intent);
        });

        // NGO Dashboard Click
        cardNgo.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NgoDashboardActivity.class);
            startActivity(intent);
        });

        // Logout Click
        cardLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // closes Home so user can't go back
        });
    }
}