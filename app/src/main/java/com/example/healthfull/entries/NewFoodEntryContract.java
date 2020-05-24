package com.example.healthfull.entries;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.search.FoodSearchResults;

public interface NewFoodEntryContract {
    interface View {
        void onSearchSuccess(FoodSearchResults results);
        void onSearchFailure(String message);
        void onAddSuccess();
        void onAddFailure(String message);
        void setResultsViewAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter {
        void search(String query);
    }

    interface Interactor {
        void performSearch(String query);
        void storeFoodLog(String id);
    }

    interface onAddFoodListener {
        void onSearchSuccess(FoodSearchResults results);
        void onSearchFailure(String message);
        void onAddSuccess();
        void onAddFailure(String message);
    }
}
