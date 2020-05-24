package com.example.healthfull.entries;

import androidx.annotation.NonNull;

import com.example.healthfull.search.FoodSearchResult;
import com.example.healthfull.search.FoodSearchResults;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * MVP Interactor is responsible for controlling models the NewFoodEntry activity interacts with
 */
public class NewFoodEntryInteractor implements NewFoodEntryContract.Interactor {

    private static final String TAG = "NewFoodEntry";

    private NewFoodEntryContract.onAddFoodListener onAddFoodListener;

    public NewFoodEntryInteractor(NewFoodEntryContract.onAddFoodListener onAddFoodListener) {
        this.onAddFoodListener = onAddFoodListener;
    }

    // Migrate to functionality to search package using a callback for the onAddFoodListener
    // callback
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
}
