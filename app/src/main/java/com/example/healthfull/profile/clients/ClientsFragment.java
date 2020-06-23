package com.example.healthfull.profile.clients;

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
import com.example.healthfull.profile.User;

public class ClientsFragment extends Fragment implements ClientsContract.View {

    private ClientsContract.Presenter presenter;

    private ProgressBar progressBar;
    private CardView addNewClientsCard;
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



    }

    @Override
    public void onClientsLoadSuccess(RecyclerView.Adapter adapter) {

    }

    @Override
    public void onClientsLoadFailure(String message) {

    }

    @Override
    public void viewEntriesForUser(User user) {

    }
}
