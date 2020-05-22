package com.example.healthfull.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthfull.MainActivity;
import com.example.healthfull.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter loginPresenter;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loadingProgressBar = findViewById(R.id.login_loading);

        // hide login
        usernameEditText.setVisibility(View.INVISIBLE);
        passwordEditText.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);

        loadingProgressBar.setVisibility(View.VISIBLE);

        loginPresenter = new LoginPresenter(this);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(
                            usernameEditText.getText().toString(),
                            passwordEditText.getText().toString()
                    );
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onStart() {
        super.onStart();

        // check for stored user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // change activity to main dashboard
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // show login
            usernameEditText.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);

            loadingProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void login(String email, String password) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginPresenter.login(email, password);
    }

    @Override
    public void onLoginSuccess(String message) {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(String message) {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInputEnabled(boolean enabled) {
        usernameEditText.setEnabled(enabled);
        passwordEditText.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }
}
