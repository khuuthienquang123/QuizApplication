package com.example.quizapplication.activities.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.user.UserActivity;
import com.example.quizapplication.activities.account_management.AccountManagement;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordValidationEditText;
    private AccountManagement accountManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        passwordValidationEditText = findViewById(R.id.password_validation);
        Button signupButton = findViewById(R.id.signup_button);
        accountManagement = new AccountManagement();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordValidation = passwordValidationEditText.getText().toString();

                if(!password.equals(passwordValidation)){
                    Toast.makeText(SignupActivity.this, "Password and password validation do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                accountManagement.signUp(email, password, new AccountManagement.SignUpCallback() {
                    @Override
                    public void onSignUpSuccess(FirebaseUser user) {
                        Toast.makeText(SignupActivity.this, "Sign up successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, UserActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onSignUpFailure(Exception e) {
                        if (e instanceof FirebaseAuthException) {
                            Toast.makeText(SignupActivity.this, "Sign up failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
