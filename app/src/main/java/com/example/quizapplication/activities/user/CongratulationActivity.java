package com.example.quizapplication.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;

public class CongratulationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        Button exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(CongratulationActivity.this, CategoriesAdapter.class);
            startActivity(intent);
            finish();
        });
    }
}