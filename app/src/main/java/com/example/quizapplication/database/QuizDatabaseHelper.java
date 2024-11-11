package com.example.quizapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quizapplication.activities.model.Quiz;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;
    private static QuizDatabaseHelper instance;

    private static final String CREATE_TABLE_QUIZZES = "CREATE TABLE quizzes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "question TEXT, " +
            "category_id INTEGER)";

    private static final String CREATE_TABLE_ANSWERS = "CREATE TABLE answers (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "quiz_id INTEGER, " +
            "text TEXT, " +
            "is_correct INTEGER)";

    public static synchronized QuizDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create categories table
        db.execSQL("CREATE TABLE categories (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        // Create answers table
        db.execSQL(CREATE_TABLE_ANSWERS);

        db.execSQL(CREATE_TABLE_QUIZZES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS quizzes");
        db.execSQL("DROP TABLE IF EXISTS answers");
        onCreate(db);
    }
}
