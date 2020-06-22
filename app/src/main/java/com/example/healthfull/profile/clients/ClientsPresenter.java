package com.example.healthfull.profile.clients;

public class ClientsPresenter implements ClientsContract.Presenter, ClientsContract.onDoneCallback {

    private ClientsContract.View view;
    private ClientsContract.Interactor interactor;

    public ClientsPresenter(ClientsContract.View view) {
        this.view = view;
        this.interactor = new ClientsInteractor(this);
    }

    @Override
    public void loadClients() {

    }
}
