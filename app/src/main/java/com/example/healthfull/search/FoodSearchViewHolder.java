package com.example.healthfull.search;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class FoodSearchViewHolder extends RecyclerView.ViewHolder {
    private ConstraintLayout layout;
    private TextView nameView;
    private Button addButton;
    private ProgressBar progressBar;
    public FoodSearchViewHolder(ConstraintLayout l) {
        super(l);
        layout = l;
        nameView = (TextView) layout.getChildAt(0);
        addButton = (Button) layout.getChildAt(1);
        progressBar = (ProgressBar) layout.getChildAt(2);
    }

    public void onAddButtonClick() {
        addButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onAddFinished() {
        addButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public Button getAddButton() {
        return addButton;
    }

    public TextView getNameView() {
        return nameView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

//    @Override
//    public void onStart() {
//        onAddButtonClick();
//    }
//
//    @Override
//    public void onFinish() {
//        onAddFinished();
//    }
}
