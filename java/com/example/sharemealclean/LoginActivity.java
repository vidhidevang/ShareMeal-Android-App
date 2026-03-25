package com.example.sharemealclean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        db = new DatabaseHelper(this);

        // Go to register page
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            String role = db.loginUser(email, password);

            if (role != null) {

                role = role.trim();

                if (role.equalsIgnoreCase("Donor")) {

                    Intent intent = new Intent(LoginActivity.this, DonorDashboardActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if (role.equalsIgnoreCase("NGO Representative") || role.equalsIgnoreCase("NGO")) {

                    Intent intent = new Intent(LoginActivity.this, NgoDashboardActivity.class);
                    startActivity(intent);
                    finish();

                }

            } else {

                Toast.makeText(LoginActivity.this,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}