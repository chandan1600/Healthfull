package com.example.healthfull.profile.clients;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.healthfull.R;
import com.example.healthfull.profile.User;

public class NewTrainerActivity extends AppCompatActivity implements NewTrainerContract.View {

    NewTrainerContract.Presenter presenter;

    private EditText searchEditText;
    private Button searchButton;
    private ProgressBar searchProgressBar;

    private CardView newTrainerCard;
    private TextView newTrainerName;
    private Button newTrainerAddButton;
    private ProgressBar newTrainerAddProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);

        setTitle("Request a New Trainer");

        presenter = new NewTrainerPresenter(this);

        searchEditText = findViewById(R.id.newfriend_search_query);
        searchButton = findViewById(R.id.newfriend_search_button);
        searchProgressBar = findViewById(R.id.newfriend_progressbar);

        newTrainerCard = findViewById(R.id.newfriend_friend_card);
        newTrainerName = findViewById(R.id.newfriend_friend_name);
        newTrainerAddButton = findViewById(R.id.newfriend_friend_add_button);
        newTrainerAddProgressBar = findViewById(R.id.newfriend_friend_progressbar);

        searchEditText.setHint(R.string.trainer_email_address);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.search(searchEditText.getText().toString());
                newTrainerCard.setVisibility(View.INVISIBLE);
                searchProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onSearchSuccess(User user, boolean addable) {
        newTrainerName.setText(user.getName());
        newTrainerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTrainerAddButton.setEnabled(false);
                newTrainerAddProgressBar.setVisibility(View.VISIBLE);
                presenter.addUser(user);
            }
        });

        newTrainerAddButton.setText(getString(R.string.request));
        newTrainerAddButton.setEnabled(true);
        newTrainerCard.setVisibility(View.VISIBLE);
        searchProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSearchFailure(String message) {
        searchProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess() {
        newTrainerAddProgressBar.setVisibility(View.INVISIBLE);
        newTrainerAddButton.setText(R.string.requested);
    }

    @Override
    public void onAddFailure(String message) {
        newTrainerAddProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        newTrainerAddButton.setEnabled(true);
    }
}
