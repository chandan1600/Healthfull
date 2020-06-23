package com.example.healthfull.profile.friends;

import com.example.healthfull.profile.User;

public class NewFriendPresenter implements NewFriendContract.Presenter, NewFriendContract.onDoneCallback {

    private NewFriendContract.View view;
    private NewFriendContract.Interactor interactor;

    public NewFriendPresenter(NewFriendContract.View view) {
        this.view = view;
        this.interactor = new NewFriendInteractor(this);
    }

    @Override
    public void search(String email) {
        interactor.performSearch(email);
    }

    @Override
    public void addFriend(User user) {
        interactor.performAddFriend(user);
    }

    @Override
    public void onSearchSuccess(User user, boolean addable) {
        view.onSearchSuccess(user, addable);
    }

    @Override
    public void onSearchFailure(String message) {
        view.onSearchFailure(message);
    }

    @Override
    public void onAddSuccess() {
        view.onAddSuccess();
    }

    @Override
    public void onAddFailure(String message) {
        view.onAddFailure(message);
    }
}
