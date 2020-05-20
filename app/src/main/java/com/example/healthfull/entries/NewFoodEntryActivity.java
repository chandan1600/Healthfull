package com.example.healthfull.entries;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.example.healthfull.search.FoodSearchResults;

public class NewFoodEntryActivity extends AppCompatActivity implements NewFoodEntryContract.View {

    private NewFoodEntryPresenter presenter;

    private EditText searchInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfoodentry);

        presenter = new NewFoodEntryPresenter(this);

        searchInput = findViewById(R.id.food_entry_query);

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    presenter.search("");
                }
                return false;
            }
        });
    }

    @Override
    public void onSearchSuccess(FoodSearchResults results) {
        Toast.makeText(getApplicationContext(), results.get(0).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
