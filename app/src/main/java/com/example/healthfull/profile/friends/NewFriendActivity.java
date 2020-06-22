package com.example.healthfull.profile.friends;

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

public class NewFriendActivity extends AppCompatActivity implements NewFriendContract.View {

    private NewFriendContract.Presenter presenter;

    private EditText searchEditText;
    private Button searchButton;
    private ProgressBar searchProgressBar;

    private CardView newFriendCard;
    private TextView newFriendName;
    private Button newFriendAddButton;
    private ProgressBar newFriendAddProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);

        setTitle("Add New Friends");

        presenter = new NewFriendPresenter(this);

        searchEditText = findViewById(R.id.newfriend_search_query);
        searchButton = findViewById(R.id.newfriend_search_button);
        searchProgressBar = findViewById(R.id.newfriend_progressbar);

        newFriendCard = findViewById(R.id.newfriend_friend_card);
        newFriendName = findViewById(R.id.newfriend_friend_name);
        newFriendAddButton = findViewById(R.id.newfriend_friend_add_button);
        newFriendAddProgressBar = findViewById(R.id.newfriend_friend_progressbar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.search(searchEditText.getText().toString());
                newFriendCard.setVisibility(View.INVISIBLE);
                searchProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onSearchSuccess(User user, boolean addable) {
        newFriendName.setText(user.getName());
        newFriendAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFriendAddButton.setEnabled(false);
                newFriendAddProgressBar.setVisibility(View.VISIBLE);
                presenter.addFriend(user);
            }
        });

        newFriendAddButton.setText(getString(R.string.add));
        newFriendAddButton.setEnabled(true);
        newFriendCard.setVisibility(View.VISIBLE);
        searchProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSearchFailure(String message) {
        searchProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess() {
        newFriendAddProgressBar.setVisibility(View.INVISIBLE);
        newFriendAddButton.setText("ADDED");
    }

    @Override
    public void onAddFailure(String message) {
        newFriendAddProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        newFriendAddButton.setEnabled(true);
    }
}
