package com.example.quizapplication.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.Login.LoginActivity;
import com.example.quizapplication.activities.account_management.AccountManagement;

public class StartActivity extends AppCompatActivity {
    AccountManagement accountManagement = new AccountManagement();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view of the activity
        setContentView(R.layout.main_activity);

        Button startButton = findViewById(R.id.start_quiz_button);
        Button signOutButton = findViewById(R.id.user_signout_button);

        startButton.setOnClickListener(v -> {
            // Start the quiz
            Intent intent = new Intent(StartActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });

        signOutButton.setOnClickListener(v -> {
            // Sign out the user
            accountManagement.signOut();
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
