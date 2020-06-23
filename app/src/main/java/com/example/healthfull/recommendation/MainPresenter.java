package com.example.healthfull.recommendation;

/**
 * responsible for handling actions from the view and updating UI
 */
public class MainPresenter implements MainContract.recPresenter {

    private MainContract.recView rView;

    MainPresenter(MainContract.recView view){
        rView = view;
    }

    // presenter methods
    @Override
    public void handleRecButton() {
        rView.showRec();
    }
}
