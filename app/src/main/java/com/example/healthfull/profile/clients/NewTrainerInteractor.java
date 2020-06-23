package com.example.healthfull.profile.clients;

import androidx.annotation.NonNull;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewTrainerInteractor implements NewTrainerContract.Interactor {

    private NewTrainerContract.onDoneCallback onDoneCallback;

    public NewTrainerInteractor(NewTrainerContract.onDoneCallback onDoneCallback) {
        this.onDoneCallback = onDoneCallback;
    }

    @Override
    public void performSearch(String email) {
        User.GetUser(email, new OnDoneListener<User>() {
            @Override
            public void onSuccess(User user) {
                if (user.getFirebaseUser().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    onDoneCallback.onSearchFailure("That's you!");
                } else {
                    onDoneCallback.onSearchSuccess(user, true);
                }
            }

            @Override
            public void onFailure(String message) {
                onDoneCallback.onSearchFailure(message);
            }
        });
    }

    @Override
    public void performAddUser(User user) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("trainer", user.getFirebaseUser())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onDoneCallback.onAddSuccess();
                        } else {
                            onDoneCallback.onAddFailure("Failed to request trainer");
                        }
                    }
                });
    }
}
