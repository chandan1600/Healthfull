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

public class NewClientActivity extends AppCompatActivity implements NewClientContract.View {

    NewClientContract.Presenter presenter;

    private EditText searchEditText;
    private Button searchButton;
    private ProgressBar searchProgressBar;

    private CardView newClientCard;
    private TextView newClientName;
    private Button newClientAddButton;
    private ProgressBar newClientAddProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);

        setTitle("Add a New Client");

        presenter = new NewClientPresenter(this);

        searchEditText = findViewById(R.id.newfriend_search_query);
        searchButton = findViewById(R.id.newfriend_search_button);
        searchProgressBar = findViewById(R.id.newfriend_progressbar);

        newClientCard = findViewById(R.id.newfriend_friend_card);
        newClientName = findViewById(R.id.newfriend_friend_name);
        newClientAddButton = findViewById(R.id.newfriend_friend_add_button);
        newClientAddProgressBar = findViewById(R.id.newfriend_friend_progressbar);

        searchEditText.setHint(R.string.client_email_address);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.search(searchEditText.getText().toString());
                newClientCard.setVisibility(View.INVISIBLE);
                searchProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onSearchSuccess(User user, boolean addable) {
        newClientName.setText(user.getName());
        newClientAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newClientAddButton.setEnabled(false);
                newClientAddProgressBar.setVisibility(View.VISIBLE);
                presenter.addUser(user);
            }
        });

        newClientAddButton.setText(getString(R.string.add));
        newClientAddButton.setEnabled(true);
        newClientCard.setVisibility(View.VISIBLE);
        searchProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSearchFailure(String message) {
        searchProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess() {
        newClientAddProgressBar.setVisibility(View.INVISIBLE);
        newClientAddButton.setText(R.string.added);
    }

    @Override
    public void onAddFailure(String message) {
        newClientAddProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        newClientAddButton.setEnabled(true);
    }
}
