package com.example.healthfull.entries;

import com.example.healthfull.search.FoodSearchResult;
import com.example.healthfull.search.FoodSearchResults;

public interface NewFoodEntryContract {
    interface View {
        void onSearchSuccess(FoodSearchResults results);
        void onSearchFailure(String message);
    }

    interface Presenter {
        void search(String query);
    }

    interface Interactor {
        void performSearch(String query);
    }

    interface onAddFoodListener {
        void onSuccess(FoodSearchResults results);
        void onFailure(String message);
    }
}
