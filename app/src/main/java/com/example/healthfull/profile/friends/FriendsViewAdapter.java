package com.example.healthfull.profile.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnViewHolderClickListener;

import java.util.List;

public class FriendsViewAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

    private List<User> friends;

    private OnViewHolderClickListener onClickListener;

    public FriendsViewAdapter(List<User> friends, OnViewHolderClickListener onClickListener) {
        this.friends = friends;
        this.onClickListener = onClickListener;
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

        holder.getCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
