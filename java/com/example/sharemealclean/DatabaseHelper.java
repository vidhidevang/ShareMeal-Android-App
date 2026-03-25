package com.example.sharemealclean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ShareMeal.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_FOOD = "food_posts";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS TABLE
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "EMAIL TEXT UNIQUE," +
                "PASSWORD TEXT," +
                "ROLE TEXT)");

        // FOOD POSTS TABLE
        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "QUANTITY TEXT," +
                "ADDRESS TEXT," +
                "IMAGE TEXT," +
                "STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    // CHECK IF EMAIL EXISTS
    public boolean emailExists(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT ID FROM " + TABLE_USERS + " WHERE EMAIL=?",
                new String[]{email}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }

    // REGISTER USER
    public boolean registerUser(String name, String email, String password, String role) {

        if (emailExists(email)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("EMAIL", email);
        values.put("PASSWORD", password);
        values.put("ROLE", role);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    // LOGIN USER
    public String loginUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT ROLE FROM " + TABLE_USERS + " WHERE EMAIL=? AND PASSWORD=?",
                new String[]{email, password}
        );

        if (cursor != null && cursor.moveToFirst()) {

            String role = cursor.getString(0);
            cursor.close();
            return role;
        }

        if (cursor != null) {
            cursor.close();
        }

        return null;
    }

    // INSERT FOOD POST (DONOR POSTS)
    public boolean insertFoodPost(String name, String quantity, String address, String imageUri) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("QUANTITY", quantity);
        values.put("ADDRESS", address);
        values.put("IMAGE", imageUri);

        // NEW DONATION SHOULD BE AVAILABLE FOR NGOs
        values.put("STATUS", "Available");

        long result = db.insert(TABLE_FOOD, null, values);

        return result != -1;
    }

    // GET ALL FOOD FOR DONOR (DONOR SHOULD SEE THEIR POSTS)
    public Cursor getAllFood() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_FOOD + " ORDER BY _id DESC",
                null
        );
    }

    // GET AVAILABLE FOOD FOR NGO
    public Cursor getAvailableFood() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_FOOD + " WHERE STATUS='Available' ORDER BY _id DESC",
                null
        );
    }

    // NGO ACCEPTS DONATION
    public void acceptDonation(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("STATUS", "Accepted");

        db.update(TABLE_FOOD, values, "_id=?", new String[]{String.valueOf(id)});
    }
}