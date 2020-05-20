package com.example.healthfull.entries;

import com.example.healthfull.search.FoodSearchResults;

public class NewFoodEntryPresenter implements NewFoodEntryContract.Presenter, NewFoodEntryContract.onAddFoodListener {
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
    public void onSuccess(FoodSearchResults results) {
        view.onSearchSuccess(results);
    }

    @Override
    public void onFailure(String message) {
        view.onSearchFailure(message);
    }
}
