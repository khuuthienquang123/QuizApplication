package com.example.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizapplication.activities.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizDao {
    private final QuizDatabaseHelper dbHelper;

    public QuizDao(Context context) {
        dbHelper = QuizDatabaseHelper.getInstance(context);
    }

    public List<Quiz> getQuizzesByCategoryId(int categoryId) {
        List<Quiz> quizzes = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("quizzes", null, "category_id = ?", new String[]{String.valueOf(categoryId)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Quiz quiz = new Quiz();
                    quiz.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    quiz.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("category_id")));
                    quiz.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow("question")));
                    quizzes.add(quiz);
                } while (cursor.moveToNext());
            }
        }
        return quizzes;
    }

    public void addQuiz(Quiz quiz) {
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("question", quiz.getQuestion());
            values.put("category_id", quiz.getCategoryId());
            db.insert("quizzes", null, values);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}