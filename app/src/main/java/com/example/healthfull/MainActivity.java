package com.example.healthfull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.RewardsSystem.Rewards;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 102;


    //for camera
    ImageView selectedImage;
    ImageView galleryImageView;
    Button cameraButton;
    Button rewardButton;
    Button galleryButton;


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

        rewardButton = findViewById(R.id.rewardButton);
        rewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRewardsPage();
            }
        });

    }

    public void openRewardsPage() {
        Intent intent = new Intent(this, Rewards.class);
        startActivity(intent);
    }

}
