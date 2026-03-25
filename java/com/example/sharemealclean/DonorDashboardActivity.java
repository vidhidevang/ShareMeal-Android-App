package com.example.sharemealclean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DonorDashboardActivity extends AppCompatActivity {

    LinearLayout cardDonate, cardStatus;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_dashboard);

        cardDonate = findViewById(R.id.cardDonate);
        cardStatus = findViewById(R.id.cardStatus);
        btnLogout = findViewById(R.id.btnLogout);

        // Open Donate Food page
        cardDonate.setOnClickListener(v -> {

            Intent intent = new Intent(DonorDashboardActivity.this, DonateActivity.class);
            startActivity(intent);

        });

        // Open My Donations page
        cardStatus.setOnClickListener(v -> {

            Intent intent = new Intent(DonorDashboardActivity.this, DonationStatusActivity.class);
            startActivity(intent);

        });

        // Logout
        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(DonorDashboardActivity.this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

        });
    }
}