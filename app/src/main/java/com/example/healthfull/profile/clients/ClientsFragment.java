package com.example.healthfull.profile.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.example.healthfull.entries.ViewEntriesActivity;
import com.example.healthfull.profile.User;

public class ClientsFragment extends Fragment implements ClientsContract.View {

    private ClientsContract.Presenter presenter;

    private ProgressBar progressBar;
    private CardView addNewClientsCard;
    private CardView addNewTrainerCard;
    private RecyclerView clientsRecyclerView;
    private LinearLayoutManager clientsLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ClientsPresenter(this);

        progressBar = view.findViewById(R.id.clients_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        addNewClientsCard = view.findViewById(R.id.clients_addnewclient_card);
        addNewClientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), NewClientActivity.class);
                startActivity(intent);
            }
        });

        addNewTrainerCard = view.findViewById(R.id.clients_addnewtrainer_card);
        addNewTrainerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), NewTrainerActivity.class);
                startActivity(intent);
            }
        });

        clientsRecyclerView = view.findViewById(R.id.clients_recyclerview);
        clientsRecyclerView.setHasFixedSize(true);

        clientsLayoutManager = new LinearLayoutManager(getContext());
        clientsRecyclerView.setLayoutManager(clientsLayoutManager);

        presenter.loadClients();
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        presenter.loadClients();
    }

    @Override
    public void onClientsLoadSuccess(RecyclerView.Adapter adapter) {
        clientsRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClientsLoadFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void viewEntriesForUser(User user) {
        Intent intent = new Intent(getContext().getApplicationContext(), ViewEntriesActivity.class);
        intent.putExtra("user", user.getFirebaseUser().getId());
        startActivity(intent);
    }
}
