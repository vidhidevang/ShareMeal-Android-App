package com.example.sharemealclean;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DonateActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    EditText etFoodName, etQuantity, etAddress;
    Button btnPost;
    Spinner spinnerTime;
    ImageView imgFood;
    FrameLayout imageContainer;
    ImageView imgPlus;
    DatabaseHelper db;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        imgPlus = findViewById(R.id.imgPlus);
        etFoodName = findViewById(R.id.etFoodName);
        etQuantity = findViewById(R.id.etQuantity);
        etAddress = findViewById(R.id.etAddress);
        btnPost = findViewById(R.id.btnPost);
        spinnerTime = findViewById(R.id.spinnerTime);
        imgFood = findViewById(R.id.imgFood);
        imageContainer = findViewById(R.id.imageContainer);

        db = new DatabaseHelper(this);

        String[] timeOptions = {
                "Cooked within 1 hour",
                "Cooked within 2 hours",
                "Cooked today",
                "Packed food"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                timeOptions
        );

        spinnerTime.setAdapter(adapter);

        imageContainer.setOnClickListener(v -> openGallery());

        btnPost.setOnClickListener(v -> {

            String name = etFoodName.getText().toString().trim();
            String quantity = etQuantity.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (name.isEmpty() || quantity.isEmpty() || address.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this, "Fill all fields & select image", Toast.LENGTH_SHORT).show();
            } else {

                boolean inserted = db.insertFoodPost(
                        name,
                        quantity,
                        address,
                        selectedImageUri.toString()
                );

                if (inserted) {
                    Toast.makeText(this, "Donation Posted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to Post Donation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE &&
                resultCode == RESULT_OK &&
                data != null) {

            selectedImageUri = data.getData();

            imgFood.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgFood.setImageURI(selectedImageUri);

            imgPlus.setVisibility(View.GONE);
        }
    }
}