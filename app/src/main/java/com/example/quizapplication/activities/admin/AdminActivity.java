package com.example.quizapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.Login.LoginActivity;
import com.example.quizapplication.activities.account_management.AccountManagement;
import com.google.firebase.auth.FirebaseUser;


public class AdminActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        Button addCategoryButton = findViewById(R.id.categories_btn);
        Button addQuizButton = findViewById(R.id.quizzes_btn);
        Button signOutButton = findViewById(R.id.signout_button);

        AccountManagement accountManagement = new AccountManagement();

        FirebaseUser currentUser = accountManagement.getCurrentUser();
        if (currentUser == null || !accountManagement.isAdmin(currentUser)) {
            Toast.makeText(this, "Access Denied. Admin only", Toast.LENGTH_SHORT).show();
            accountManagement.signOut();
            finish();
        }

        addCategoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AdminManageActivityCategories.class);
            startActivity(intent);
        });

        addQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AdminManagementActivityQuizzes.class);
            startActivity(intent);
        });

        signOutButton.setOnClickListener(v -> {
            accountManagement.signOut();
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
