package com.example.healthfull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthfull.entries.NewFoodEntryActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 102;


    //for camera
    ImageView selectedImage;
    ImageView galleryImageView;
    Button cameraButton;

    Button galleryButton;
    ImageButton addEntryButton;

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
    }





}
