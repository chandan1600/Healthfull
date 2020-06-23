package com.example.healthfull.profile.friends;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnViewHolderClickListener;

import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter, FriendsContract.onFriendsLoadCallback {

    private FriendsContract.View view;
    private FriendsContract.Interactor interactor;

    public FriendsPresenter(FriendsContract.View view) {
        this.view = view;
        this.interactor = new FriendsInteractor(this);
    }

    @Override
    public void loadFriends() {
        interactor.performFriendsLoad();
    }

    @Override
    public void onFriendsLoadSuccess(List<User> friendList) {
        view.onFriendsLoadSuccess(new FriendsViewAdapter(friendList, new OnViewHolderClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder) {
                FriendsViewHolder holder = (FriendsViewHolder) viewHolder;
                view.viewEntriesForUser(holder.getUser());
            }
        }));
    }

    @Override
    public void onFriendsLoadFailure(String message) {
        view.onFriendsLoadFailure(message);
    }
}
