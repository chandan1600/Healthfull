package com.example.healthfull.entries;

import android.view.View;

import com.example.healthfull.search.FoodSearchResults;

public class NewFoodEntryPresenter implements NewFoodEntryContract.Presenter, NewFoodEntryContract.onAddFoodListener, View.OnClickListener {
    private NewFoodEntryContract.View view;
    private NewFoodEntryContract.Interactor interactor;

    public NewFoodEntryPresenter(NewFoodEntryContract.View view) {
        this.view = view;
        this.interactor = new NewFoodEntryInteractor(this);
    }

    @Override
    public void search(String query) {
        interactor.performSearch(query);
    }

    @Override
    public void onSearchSuccess(FoodSearchResults results) {
        view.onSearchSuccess(results);
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

    @Override
    public void onClick(View v) {
        interactor.storeFoodLog(v.getTag().toString());
    }
}
