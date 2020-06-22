package com.example.healthfull.profile.friends;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.MVPContract;

import java.util.List;

/**
 * FriendsContract interface declares what methods are required across the MVP architectural
 * activity
 */
public interface FriendsContract extends MVPContract {
    /**
     * Methods the view must implement, usually callbacks from the Presenter
     */
    interface View {
        void onFriendsLoadSuccess(RecyclerView.Adapter adapter);
        void onFriendsLoadFailure(String message);
    }

    /**
     * Methods the presenter must implement
     */
    interface Presenter {
        void loadFriends();
    }

    /**
     * Methods the interactor must implement to interact with the model(s)
     */
    interface Interactor {
        void performFriendsLoad();
    }

    /**
     * Callback interface, from the Interactor to the Presenter
     */
    interface onFriendsLoadCallback {
        void onFriendsLoadSuccess(List<User> friendList);
        void onFriendsLoadFailure(String message);
    }
}
