package com.example.healthfull.profile.friends;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.MVPContract;

/**
 * NewFriendContract interface declares what methods are required across the MVP architectural
 * activity
 */
public interface NewFriendContract extends MVPContract {
    /**
     * Methods the view must implement, usually callbacks from the Presenter
     */
    interface View {
        void onSearchSuccess(User user, boolean addable);
        void onSearchFailure(String message);
        void onAddSuccess();
        void onAddFailure(String message);
    }

    /**
     * Methods the presenter must implement
     */
    interface Presenter {
        void search(String email);
        void addUser(User user);
    }

    /**
     * Methods the interactor must implement to interact with the model(s)
     */
    interface Interactor {
        void performSearch(String email);
        void performAddUser(User user);
    }

    /**
     * Callback interface, from the Interactor to the Presenter
     */
    interface onDoneCallback {
        void onSearchSuccess(User user, boolean addable);
        void onSearchFailure(String message);
        void onAddSuccess();
        void onAddFailure(String message);
    }
}
