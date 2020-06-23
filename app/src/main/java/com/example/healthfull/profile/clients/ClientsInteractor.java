package com.example.healthfull.profile.clients;

public class ClientsInteractor implements ClientsContract.Interactor {

    private ClientsContract.onDoneCallback onDoneCallback;

    public ClientsInteractor(ClientsContract.onDoneCallback onDoneCallback) {
        this.onDoneCallback = onDoneCallback;
    }

    @Override
    public void performClientsLoad() {

    }
}
