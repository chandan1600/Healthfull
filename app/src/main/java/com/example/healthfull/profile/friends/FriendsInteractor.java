package com.example.healthfull.profile.friends;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnDoneListener;

import java.util.List;

public class FriendsInteractor implements FriendsContract.Interactor {

    private FriendsContract.onFriendsLoadCallback callback;

    public FriendsInteractor(FriendsContract.onFriendsLoadCallback view) {
        this.callback = view;
    }

    @Override
    public void performFriendsLoad() {
        User.GetFriends(new OnDoneListener<List<User>>() {
            @Override
            public void onSuccess(List<User> friends) {
                callback.onFriendsLoadSuccess(friends);
            }

            @Override
            public void onFailure(String message) {
                callback.onFriendsLoadFailure(message);
            }
        });
    }
}
