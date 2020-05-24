package com.example.healthfull.entries;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthfull.search.FoodSearchResult;
import com.example.healthfull.search.FoodSearchResults;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewFoodEntryInteractor implements NewFoodEntryContract.Interactor {

    private static final String TAG = "NewFoodEntry";


    private NewFoodEntryContract.onAddFoodListener onAddFoodListener;

    public NewFoodEntryInteractor(NewFoodEntryContract.onAddFoodListener onAddFoodListener) {
        this.onAddFoodListener = onAddFoodListener;
    }

    @Override
    public void performSearch(final String query) {
        // query food database
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Query q = firestore.collection("food").whereArrayContains("tags", query.toLowerCase());

        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    FoodSearchResults results = new FoodSearchResults();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        results.add(new FoodSearchResult(doc.getId(), doc.getData().get("name").toString()));
                    }
                    onAddFoodListener.onSearchSuccess(results);
                } else {
                    onAddFoodListener.onSearchFailure(task.getException().toString());
                }
            }
        });
    }

    @Override
    public void storeFoodLog(String id) {

    }
}
