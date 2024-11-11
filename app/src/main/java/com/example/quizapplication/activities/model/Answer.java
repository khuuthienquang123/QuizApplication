package com.example.quizapplication.activities.model;

import androidx.annotation.NonNull;

public class Answer {
    private int id;
    private int quizId;
    private String text;
    private boolean isCorrect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @NonNull
    @Override
    public String toString() {
        return text + (isCorrect ? " (Correct)" : "");
    }
}
