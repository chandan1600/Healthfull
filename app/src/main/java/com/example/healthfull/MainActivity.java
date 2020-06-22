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

import com.example.healthfull.DailyTarget.DailyTarget;
import com.example.healthfull.RewardsSystem.Rewards;
import com.example.healthfull.gallery.Gallery;
import com.example.healthfull.login.LoginActivity;
import com.example.healthfull.search_nutri.NutritionInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.example.healthfull.entries.NewFoodEntryActivity;

public class MainActivity extends AppCompatActivity {

    ImageView galleryImageView;
    Button cameraButton;
    Button rewardButton;
    Button galleryButton;
    Button nutriButton;
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
            //Toast.makeText(MainActivity.this, "Gallery button clicked", Toast.LENGTH_SHORT).show();
            Intent startIntent = new Intent(getApplicationContext(), Gallery.class);
            startActivity(startIntent);
        }}
        );

        nutriButton = findViewById(R.id.nutriButton);
        nutriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Nutrition button clicked", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(getApplicationContext(), NutritionInfo.class);
                startActivity(startIntent);
            }
        });

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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

}
