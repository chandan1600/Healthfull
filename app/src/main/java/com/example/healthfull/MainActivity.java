package com.example.healthfull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.RewardsSystem.Rewards;
import com.example.healthfull.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.healthfull.entries.NewFoodEntryActivity;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 102;

    //for camera
    ImageView selectedImage;
    ImageView galleryImageView;
    Button cameraButton;
    Button rewardButton;
    Button galleryButton;
    ImageButton addEntryButton;
    Button goalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryImageView = findViewById(R.id.galleryImageView);
        //selectedImage = findViewById(R.id.displayImageView);
        cameraButton = findViewById(R.id.cameraButton);
        galleryButton = findViewById(R.id.galleryButton);

        galleryButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //intent - this is what we want to happen, android is going to try to make that happen
            Intent startIntent = new Intent(getApplicationContext(), Gallery.class);

            //how to parse info to the another activity
            //this sends extra info to another activity as a bundle and the other activity can
            //unbundle this info and use it
            startIntent.putExtra("com.example.quicklauncher.SOMETHING","HELLO WORLD!");
            startActivity(startIntent);
        }}
        );

        addEntryButton = findViewById(R.id.main_addButton);
        addEntryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewFoodEntryActivity.class);
                startActivity(intent);
            }
        });

        goalButton = findViewById(R.id.buttonGoal);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyTarget.class);
                startActivity(intent);
            }
        });

        rewardButton = findViewById(R.id.rewardButton);
        rewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Rewards.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

}
