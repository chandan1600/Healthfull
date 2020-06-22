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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.DailyTarget.DailyTarget;
import com.example.healthfull.RewardsSystem.Rewards;
import com.example.healthfull.entries.ViewEntriesActivity;
import com.example.healthfull.gallery.Gallery;
import com.example.healthfull.login.LoginActivity;
import com.example.healthfull.profile.ProfileActivity;
import com.example.healthfull.search_nutri.NutritionInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.example.healthfull.entries.NewFoodEntryActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;
public class MainActivity extends AppCompatActivity implements MainContract.View {

    public static final int CAMERA_REQUEST_CODE = 102;

    private static final String TAG = "Main";

    MainContract.Presenter presenter;

    //for camera
    ImageView selectedImage;
    ImageView galleryImageView;
    Button profileButton;
    ImageButton addEntryButton;
    Button cameraButton;
    Button rewardButton;
    Button galleryButton;
    Button nutriButton;
    Button goalButton;
    Button viewEntriesButton;
    ProgressBar addWaterProgressBar;
    Button addWaterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        TextView welcomeTitle = findViewById(R.id.welcomeUserTextView);
        welcomeTitle.setText("Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        galleryImageView = findViewById(R.id.galleryImageView);
        //selectedImage = findViewById(R.id.displayImageView);
        cameraButton = findViewById(R.id.cameraButton);
        galleryButton = findViewById(R.id.galleryButton);

        profileButton = findViewById(R.id.main_profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

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

        viewEntriesButton = findViewById(R.id.main_viewentries_button);
        viewEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewEntriesActivity.class);
                intent.putExtra("user", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(intent);
            }
        });

        addWaterButton = findViewById(R.id.main_addwater_button);
        addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addWater();
            }
        });

        // add water progress bar
        addWaterProgressBar = findViewById(R.id.main_addwater_progressbar);
        addWaterProgressBar.setVisibility(View.INVISIBLE);

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
    @Override
    public void onAddWaterSuccess() {
        Toast.makeText(getApplicationContext(), "Water added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWaterFailure(String message) {
        Toast.makeText(getApplicationContext(), "Failed to add water, please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAddWaterButtonAvailable(boolean enabled) {
        addWaterProgressBar.setVisibility(enabled ? View.INVISIBLE : View.VISIBLE);
        addWaterButton.setEnabled(enabled);
    }

}
