package com.example.healthfull.login;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.onLoginListener onLoginListener;

    public LoginInteractor(LoginContract.onLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Override
    public void performFirebaseLogin(String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginListener.onSuccess();
                        } else {
                            onLoginListener.onFailure(task.getException().toString());
                        }
                    }
                });
    }
}
