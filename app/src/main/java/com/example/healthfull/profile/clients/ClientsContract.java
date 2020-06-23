package com.example.healthfull.profile.clients;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.MVPContract;

import java.util.List;

/**
 * ClientsContract interface declares what methods are required across the MVP architectural
 * activity
 */
public interface ClientsContract extends MVPContract {
    /**
     * Methods the view must implement, usually callbacks from the Presenter
     */
    interface View {
        void onClientsLoadSuccess(RecyclerView.Adapter adapter);
        void onClientsLoadFailure(String message);
        void viewEntriesForUser(User user);
    }

    /**
     * Methods the presenter must implement
     */
    interface Presenter {
        void loadClients();
    }

    /**
     * Methods the interactor must implement to interact with the model(s)
     */
    interface Interactor {
        void performClientsLoad(String trainerId);
    }

    /**
     * Callback interface, from the Interactor to the Presenter
     */
    interface onDoneCallback {
        void onClientsLoadSuccess(List<User> clientList);
        void onClientsLoadFailure(String message);
    }
}
