package com.example.healthfull.profile.clients;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnDoneListener;

import java.util.List;

public class ClientsInteractor implements ClientsContract.Interactor {

    private ClientsContract.onDoneCallback onDoneCallback;

    public ClientsInteractor(ClientsContract.onDoneCallback onDoneCallback) {
        this.onDoneCallback = onDoneCallback;
    }

    @Override
    public void performClientsLoad(String trainerId) {
        User.GetClients(trainerId, new OnDoneListener<List<User>>() {
            @Override
            public void onSuccess(List<User> clients) {
                onDoneCallback.onClientsLoadSuccess(clients);
            }

            @Override
            public void onFailure(String message) {
                onDoneCallback.onClientsLoadFailure(message);
            }
        });
    }
}
