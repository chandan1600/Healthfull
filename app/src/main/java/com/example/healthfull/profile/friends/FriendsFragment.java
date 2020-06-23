package com.example.healthfull.profile.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.example.healthfull.entries.ViewEntriesActivity;
import com.example.healthfull.profile.User;

public class FriendsFragment extends Fragment implements FriendsContract.View {

    private FriendsContract.Presenter presenter;

    private ProgressBar progressBar;
    private CardView addNewFriendsCard;
    private RecyclerView friendsRecyclerView;
    private LinearLayoutManager friendsLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new FriendsPresenter(this);

        progressBar = view.findViewById(R.id.friends_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        addNewFriendsCard = view.findViewById(R.id.friends_addnew_card);
        addNewFriendsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), NewFriendActivity.class);
                startActivity(intent);
            }
        });

        friendsRecyclerView = view.findViewById(R.id.friends_recyclerview);
        friendsRecyclerView.setHasFixedSize(true);

        friendsLayoutManager = new LinearLayoutManager(getContext());
        friendsRecyclerView.setLayoutManager(friendsLayoutManager);

        presenter.loadFriends();
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        presenter.loadFriends();
    }

    @Override
    public void onFriendsLoadSuccess(RecyclerView.Adapter adapter) {
        friendsRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFriendsLoadFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void viewEntriesForUser(User user) {
        Intent intent = new Intent(getContext().getApplicationContext(), ViewEntriesActivity.class);
        intent.putExtra("user", user.getFirebaseUser().getId());
        startActivity(intent);
    }
}
