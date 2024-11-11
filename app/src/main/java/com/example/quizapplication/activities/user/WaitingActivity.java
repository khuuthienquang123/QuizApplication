package com.example.quizapplication.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Category;
import com.example.quizapplication.database.CategoryDao;

public class WaitingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        TextView categoryNameText = findViewById(R.id.category_name_text);
        Button startQuizButton = findViewById(R.id.start_quiz_button);

        int categoryId = getIntent().getIntExtra("categoryId", -1);
        if (categoryId != -1){
            CategoryDao categoryDao = new CategoryDao(this);
            Category category = categoryDao.getCategoryById(categoryId);

            if (category != null){
                categoryNameText.setText(category.getName());
            }
        }

        startQuizButton.setOnClickListener(v -> {
            // Start the quiz
            Intent intent = new Intent(WaitingActivity.this, QuizActivity.class);
            intent.putExtra("categoryId", categoryId);
            Log.d("QuizActivity", "Sending categoryId: " + categoryId);
            startActivity(intent);
        });
    }
}
