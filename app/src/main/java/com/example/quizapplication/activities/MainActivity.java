package com.example.quizapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.account_management.AccountManagement;
import com.example.quizapplication.activities.Login.*;
import com.example.quizapplication.activities.admin.AdminActivity;
import com.example.quizapplication.activities.user.UserActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // No user is signed in, direct to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Check user role
            checkUserRole(currentUser);
        }
    }

    public void checkUserRole(FirebaseUser user) {
        AccountManagement accountManagement = new AccountManagement();
        if (accountManagement.isAdmin(user)) {
            // Direct to AdminActivity
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        } else {
            // Direct to UserActivity
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
        finish();
    }
}