package com.example.healthfull.profile.friends;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.example.healthfull.profile.User;

import java.util.List;

public class FriendsViewAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

    private List<User> friends;

    public FriendsViewAdapter(List<User> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_friend, parent, false);

        FriendsViewHolder holder = new FriendsViewHolder(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.setUser(friends.get(position));

        holder.getName().setText(friends.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
