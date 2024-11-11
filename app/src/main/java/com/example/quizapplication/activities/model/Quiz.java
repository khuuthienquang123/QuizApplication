package com.example.quizapplication.activities.model;

import androidx.annotation.NonNull;

public class Quiz {
    private int id;
    private int categoryId;
    private String question;
    private String correctAnswer;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @NonNull
    @Override
    public String toString() {
        return question;
    }
}
