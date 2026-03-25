package com.example.sharemealclean;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class NgoDashboardActivity extends AppCompatActivity {

    ListView lvDonations;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngodashboard);

        lvDonations = findViewById(R.id.lvDonations);
        db = new DatabaseHelper(this);

        displayPosts();
    }

    private void displayPosts() {

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
                R.layout.item_food_card,
                cursor,
                from,
                to,
                0
        );

        adapter.setViewBinder((view, cursor1, columnIndex) -> {

            if (view.getId() == R.id.ivFoodImg) {

                ImageView imageView = (ImageView) view;

                try {

                    String imageUri = cursor1.getString(columnIndex);

                    if (imageUri != null && !imageUri.isEmpty()) {
                        Uri uri = Uri.parse(imageUri);
                        imageView.setImageURI(uri);
                    } else {
                        imageView.setImageResource(R.drawable.logo_white);
                    }

                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.logo_white);
                }

                return true;
            }

            if (view.getId() == R.id.btnAccept) {

                Button btn = (Button) view;

                int id = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));

                btn.setOnClickListener(v -> {

                    db.acceptDonation(id);
                    displayPosts();

                });

                return true;
            }

            return false;
        });

        lvDonations.setAdapter(adapter);
    }
}