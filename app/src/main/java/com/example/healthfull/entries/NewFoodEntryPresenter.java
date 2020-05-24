package com.example.healthfull.entries;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.search.FoodSearchResults;
import com.example.healthfull.search.FoodSearchResultsAdapter;
import com.example.healthfull.search.FoodSearchViewHolder;
import com.example.healthfull.util.OnDoneListener;
import com.example.healthfull.util.OnViewHolderAddListener;

public class NewFoodEntryPresenter implements NewFoodEntryContract.Presenter, NewFoodEntryContract.onAddFoodListener, OnViewHolderAddListener {
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
        view.setResultsViewAdapter(new FoodSearchResultsAdapter(results, this));
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
    public void onAdd(RecyclerView.ViewHolder viewHolder) {
        FoodSearchViewHolder holder = (FoodSearchViewHolder) viewHolder;
        holder.onAddButtonClick();
        NewFoodEntryAdder adder = new NewFoodEntryAdder(holder.getAddButton().getTag().toString());
        adder.setOnDoneListener(new OnDoneListener() {
            @Override
            public void onSuccess(Object object) {
                holder.onAddFinished();
                onAddSuccess();
            }

            @Override
            public void onFailure(String message) {
                onAddFailure(message);
            }
        });
        adder.save();
    }
}
