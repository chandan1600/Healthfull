package com.example.healthfull.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.onLoginListener onLoginListener;

    public LoginInteractor(LoginContract.onLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Override
    public void performFirebaseLogin(final String email, final String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginListener.onSuccess("Logged In Successfully");
                        } else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidUserException e) {
                                // User does not exist,
                                createUser(email, password);
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                // Invalid password, user exists
                                onLoginListener.onFailure("Password Incorrect");
                            } catch(Exception e) {
                                Log.e("Healthfull App", e.getMessage());
                            }
                        }
                    }
                });
    }

    private void createUser(String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginListener.onSuccess("Registered Successfully");
                        } else {
                            onLoginListener.onFailure(task.getException().toString());
                        }
                    }
                });
    }
}
