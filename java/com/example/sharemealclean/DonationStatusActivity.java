package com.example.sharemealclean;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class DonationStatusActivity extends AppCompatActivity {

    ListView lvDonations;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_status);

        lvDonations = findViewById(R.id.lvDonations);
        db = new DatabaseHelper(this);

        displayDonations();
    }

    private void displayDonations() {

        Cursor cursor = db.getAllFood();

        String[] from = {
                "NAME",
                "QUANTITY",
                "ADDRESS",
                "STATUS",
                "IMAGE"
        };

        int[] to = {
                R.id.txtFoodTitle,
                R.id.txtFoodQuantity,
                R.id.txtAddress,
                R.id.txtStatus,
                R.id.ivFoodImg
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.item_food_card_donor,
                cursor,
                from,
                to,
                0
        );

        adapter.setViewBinder((view, cursor1, columnIndex) -> {

            if (view.getId() == R.id.ivFoodImg) {

                ImageView imageView = (ImageView) view;

                try {

                    String imageUri = cursor1.getString(
                            cursor1.getColumnIndexOrThrow("IMAGE")
                    );

                    if (imageUri != null && !imageUri.isEmpty()) {
                        imageView.setImageURI(Uri.parse(imageUri));
                    }

                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.logo_white);
                }

                return true;
            }

            // Hide Accept button for donors
            if (view.getId() == R.id.btnAccept) {
                view.setVisibility(View.GONE);
                return true;
            }

            return false;
        });

        lvDonations.setAdapter(adapter);
    }
}
