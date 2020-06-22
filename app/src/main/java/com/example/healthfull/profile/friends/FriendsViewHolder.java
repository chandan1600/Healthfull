package com.example.healthfull.profile.friends;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.profile.User;

public class FriendsViewHolder extends RecyclerView.ViewHolder {

    private User user;

    private ConstraintLayout layout;
    private ImageView profileImage;
    private TextView name;

    public FriendsViewHolder(ConstraintLayout layout) {
        super(layout);
        this.layout = ((ConstraintLayout)((CardView)layout.getChildAt(0)).getChildAt(0));
        profileImage = (ImageView) this.layout.getChildAt(0);
        name = (TextView) this.layout.getChildAt(1);
    }

    public User getUser() {
        return user;
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public TextView getName() {
        return name;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
