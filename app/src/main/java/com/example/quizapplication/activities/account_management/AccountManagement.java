package com.example.quizapplication.activities.account_management;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AccountManagement {
    private final FirebaseAuth mAuth;

    public AccountManagement() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password, final SignUpCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSignUpSuccess(user);
                    } else {
                        Exception e = task.getException();
                        Log.e("SignUpError", "Sign-up failed", e);
                        callback.onSignUpFailure(task.getException());
                    }
                });
    }

    public void signIn(String email, String password, final SignInCallback callback) {
        final boolean[] loginSuccess = {false};
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSignInSuccess(user);
                        loginSuccess[0] = true;
                    } else {
                        Exception e = task.getException();
                        Log.e("SignInError", "Sign-in failed", e);
                        loginSuccess[0] = false;
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public interface SignUpCallback {
        void onSignUpSuccess(FirebaseUser user);
        void onSignUpFailure(Exception e);
    }

    public interface SignInCallback {
        void onSignInSuccess(FirebaseUser user);
        void onSignInFailure(Exception e);
    }

    public boolean isAdmin(FirebaseUser user) {
        return Objects.equals(user.getEmail(), "admin@rmit.edu.vn");
    }
}
