package com.example.healthfull.entries;

import com.example.healthfull.search.FoodSearch;
import com.example.healthfull.search.FoodSearchResults;

public class NewFoodEntryInteractor implements NewFoodEntryContract.Interactor {

    private NewFoodEntryContract.onAddFoodListener onAddFoodListener;

    public NewFoodEntryInteractor(NewFoodEntryContract.onAddFoodListener onAddFoodListener) {
        this.onAddFoodListener = onAddFoodListener;
    }

    @Override
    public void performSearch(final String query) {
        FoodSearchResults results = FoodSearch.Search(query);
        onAddFoodListener.onSuccess(results);
    }
}
