package com.example.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quizapplication.activities.model.Answer;
import com.example.quizapplication.activities.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class AnswerDao {
    private final QuizDatabaseHelper databaseHelper;

    public AnswerDao(Context context) {
        databaseHelper = QuizDatabaseHelper.getInstance(context);
    }

    public void addAnswer(Answer answer){
        SQLiteDatabase db = null;

        try{
            db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("quiz_id", answer.getQuizId());
            values.put("text", answer.getText());
            values.put("is_correct", answer.isCorrect() ? 1 : 0);
            db.insert("answers", null, values);
        } finally {
            if (db != null && db.isOpen()){
                db.close();
            }
        }
    }

    public List<Answer> getAnswersByQuizId(int quizId) {
        List<Answer> answers = new ArrayList<>();

        try (SQLiteDatabase db = databaseHelper.getReadableDatabase();
             Cursor cursor = db.query("answers", null, "quiz_id = ?", new String[]{String.valueOf(quizId)}, null, null, null)) {
            Log.d("AnswerDao", "Query executed for quizId: " + quizId);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Answer answer = new Answer();
                    answer.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    answer.setQuizId(cursor.getInt(cursor.getColumnIndexOrThrow("quiz_id")));
                    answer.setText(cursor.getString(cursor.getColumnIndexOrThrow("text")));
                    answer.setCorrect(cursor.getInt(cursor.getColumnIndexOrThrow("is_correct")) == 1);
                    answers.add(answer);
                } while (cursor.moveToNext());
            } else {
                Log.d("AnswerDao", "No answers found for quizId: " + quizId);
            }
        }
        return answers;
    }

    public void updateAnswer(Answer answer) {
        SQLiteDatabase db = null;

        try {
            db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("text", answer.getText());
            values.put("is_correct", answer.isCorrect() ? 1 : 0);
            db.update("answers", values, "id = ?", new String[]{String.valueOf(answer.getId())});
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public void logAnswersTable() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseHelper.getReadableDatabase();
            cursor = db.query("answers", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    int quizId = cursor.getInt(cursor.getColumnIndexOrThrow("quiz_id"));
                    String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                    boolean isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow("is_correct")) == 1;

                    Log.d("AnswerDao", "ID: " + id + ", Quiz ID: " + quizId + ", Text: " + text + ", Is Correct: " + isCorrect);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}
