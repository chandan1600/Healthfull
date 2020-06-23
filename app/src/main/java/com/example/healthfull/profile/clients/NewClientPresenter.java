package com.example.healthfull.profile.clients;

import com.example.healthfull.profile.User;

public class NewClientPresenter implements NewClientContract.Presenter, NewClientContract.onDoneCallback {

    private NewClientContract.View view;
    private NewClientContract.Interactor interactor;

    public NewClientPresenter(NewClientContract.View view) {
        this.view = view;
        this.interactor = new NewClientInteractor(this);
    }

    @Override
    public void search(String email) {
        interactor.performSearch(email);
    }

    @Override
    public void addUser(User user) {
        interactor.performAddUser(user);
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
