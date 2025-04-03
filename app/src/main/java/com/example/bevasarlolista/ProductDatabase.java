package com.example.bevasarlolista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProductDatabase {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ProductDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addProduct(String productName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, productName);
        database.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
    }

    public ArrayList<String> getAllProducts() {
        ArrayList<String> products = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUCTS,
                new String[]{DatabaseHelper.COLUMN_NAME},
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return products;
    }
}
