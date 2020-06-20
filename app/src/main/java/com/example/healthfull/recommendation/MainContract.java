package com.example.healthfull.recommendation;

/**
 * defines contract between view {@link MainRec} and presenter {@link MainPresenter}
 */
public interface MainContract {
    interface recView{
        void showRec();
    }

    interface recPresenter{
        void handleRecButton();
    }
}
