package com.example.healthfull.profile.clients;

import androidx.annotation.NonNull;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NewClientInteractor implements NewClientContract.Interactor {

    private NewClientContract.onDoneCallback onDoneCallback;

    public NewClientInteractor(NewClientContract.onDoneCallback onDoneCallback) {
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
                    boolean isAddable = false;

                    if (user.getTrainer().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        isAddable = true;
                    }

                    onDoneCallback.onSearchSuccess(user, isAddable);
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
        User.GetClients(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                new OnDoneListener<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {

                // check if user is already a friend
                for (User u : users) {
                    if (u.getFirebaseUser().getId().equals(user.getFirebaseUser().getId())) {
                        onDoneCallback.onAddFailure("Already Added");
                        return;
                    }
                }

                users.add(user);

                List<DocumentReference> clientRefs = new ArrayList<>();

                for (User u : users) {
                    clientRefs.add(u.getFirebaseUser());
                }

                // add friend
                FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update("clients", clientRefs)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    onDoneCallback.onAddSuccess();
                                } else {
                                    onDoneCallback.onAddFailure("Failed to add client");
                                }
                            }
                        });
            }

            @Override
            public void onFailure(String message) {
                onDoneCallback.onAddFailure(message);
            }
        });

//        FirebaseFirestore
//                .getInstance()
//                .collection("users")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .update("trainer", user.getFirebaseUser())
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            onDoneCallback.onAddSuccess();
//                        } else {
//                            onDoneCallback.onAddFailure("Failed to request trainer");
//                        }
//                    }
//                });
    }
}
