package com.example.quizapplication.database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quizapplication.activities.model.Category;

import java.util.List;
import java.util.ArrayList;

public class CategoryDao {
    private final QuizDatabaseHelper dbHelper;

    public CategoryDao(Context context) {
        dbHelper = QuizDatabaseHelper.getInstance(context);
    }

    public void viewTable(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    StringBuilder row = new StringBuilder();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        row.append(cursor.getColumnName(i)).append(": ").append(cursor.getString(i)).append(" | ");
                    }
                    Log.d("DatabaseRow", row.toString());
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase(); Cursor cursor = db.query("categories", null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    category.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    categories.add(category);
                } while (cursor.moveToNext());
            }
        }

        return categories;
    }

    public void addCategory(Category category){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        db.insert("categories", null, values);
        db.close();
    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        int rowsAffected = db.update("categories", values, "id = ?", new String[]{String.valueOf(category.getId())});
        db.close();
        return rowsAffected;
    }

    public int deleteCategory(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("categories", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    public Category getCategoryById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("categories", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Category category = null;
        if (cursor != null && cursor.moveToFirst()) {
            category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            category.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return category;
    }
}
