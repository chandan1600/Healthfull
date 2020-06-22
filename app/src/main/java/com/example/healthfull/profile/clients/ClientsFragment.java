package com.example.healthfull.profile.clients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthfull.R;

public class ClientsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView newTrainerText = view.findViewById(R.id.clients_addnewtrainer_text);
        newTrainerText.setText("Tap here to search for a trainer");

        TextView newClientText = view.findViewById(R.id.clients_addnewclient_text);
        newClientText.setText("Tap here to accept clients");
    }
}
