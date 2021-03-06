package com.example.healthfull.profile.clients;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.profile.User;
import com.example.healthfull.profile.friends.FriendsViewAdapter;
import com.example.healthfull.profile.friends.FriendsViewHolder;
import com.example.healthfull.util.OnViewHolderClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ClientsPresenter implements ClientsContract.Presenter, ClientsContract.onDoneCallback {

    private ClientsContract.View view;
    private ClientsContract.Interactor interactor;

    public ClientsPresenter(ClientsContract.View view) {
        this.view = view;
        this.interactor = new ClientsInteractor(this);
    }

    @Override
    public void loadClients() {
        interactor.performClientsLoad(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void onClientsLoadSuccess(List<User> clientList) {
        view.onClientsLoadSuccess(new FriendsViewAdapter(clientList, new OnViewHolderClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder) {
                FriendsViewHolder holder = (FriendsViewHolder) viewHolder;
                view.viewEntriesForUser(holder.getUser());
            }
        }));
    }

    @Override
    public void onClientsLoadFailure(String message) {
        view.onClientsLoadFailure(message);
    }
}
