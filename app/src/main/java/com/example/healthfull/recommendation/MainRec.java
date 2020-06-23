package com.example.healthfull.recommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.healthfull.R;

/**
 * displays the screen
 */

public class MainRec extends AppCompatActivity implements MainContract.recView {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        mPresenter = new MainPresenter(this);
    }

    // mvp view methods
    @Override
    public void showRec() {
        Toast.makeText(this,"showing recommendation", Toast.LENGTH_SHORT).show();
    }
}