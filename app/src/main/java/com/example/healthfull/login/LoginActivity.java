package com.example.healthfull.login;

import android.content.Intent;
import android.os.Bundle;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);


        loginPresenter = new LoginPresenter(this);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginPresenter.login(
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
                loginPresenter.login(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
            }
        });
    }

    protected void onStart() {
        super.onStart();

        FirebaseUser loggedIn = FirebaseAuth.getInstance().getCurrentUser();
        if (loggedIn != null) {
            // change activity to main dashboard
        }
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInputEnabled(boolean enabled) {
        usernameEditText.setEnabled(enabled);
        passwordEditText.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }
}
