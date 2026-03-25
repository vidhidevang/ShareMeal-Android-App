package com.example.sharemealclean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button btnRegister;
    DatabaseHelper db;

    String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        radioGroup = findViewById(R.id.radioGroup);
        btnRegister = findViewById(R.id.btnRegister);

        db = new DatabaseHelper(this);

        // Get data from register page
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        btnRegister.setOnClickListener(v -> {

            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(RoleSelectionActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadio = findViewById(selectedId);
            String role;

            if (selectedRadio.getText().toString().contains("Donor")) {
                role = "Donor";
            } else {
                role = "NGO";
            }

            boolean result = db.registerUser(name, email, password, role);

            if (result) {

                Toast.makeText(RoleSelectionActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RoleSelectionActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {

                Toast.makeText(RoleSelectionActivity.this, "Registration Failed. Try different email.", Toast.LENGTH_LONG).show();

            }
        });
    }
}