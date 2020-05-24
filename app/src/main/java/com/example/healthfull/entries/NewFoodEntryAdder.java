package com.example.healthfull.entries;

import androidx.annotation.NonNull;

import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewFoodEntryAdder {

    private OnDoneListener onDoneListener;
    private String foodId;

    public NewFoodEntryAdder(String foodId) {
        this.foodId = foodId;
    }

    public void setOnDoneListener(OnDoneListener onDoneListener) {
        this.onDoneListener = onDoneListener;
    }

    public void save() {
        // add this to a user collection
        Map<String, Object> entry = new HashMap<>();
        entry.put("foodId", foodId);
        entry.put("date", new Date());

        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth
                        .getInstance()
                        .getCurrentUser()
                        .getUid()
                )
                .collection("logs")
                .document()
                .set(entry)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (onDoneListener != null) {
                                onDoneListener.onSuccess(null);
                            }
                        } else {
                            if (onDoneListener != null) {
                                onDoneListener.onFailure(task.getException().toString());
                            }
                        }
                    }
                });
    }
}
