package com.example.healthfull.profile.clients;

import com.example.healthfull.profile.User;

public class NewTrainerPresenter implements NewTrainerContract.Presenter, NewTrainerContract.onDoneCallback {

    private NewTrainerContract.View view;
    private NewTrainerContract.Interactor interactor;

    public NewTrainerPresenter(NewTrainerContract.View view) {
        this.view = view;
        this.interactor = new NewTrainerInteractor(this);
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
