package com.example.healthfull.profile.friends;

import androidx.annotation.NonNull;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewFriendInteractor implements NewFriendContract.Interactor {

    private NewFriendContract.onDoneCallback onDoneCallback;

    public NewFriendInteractor(NewFriendContract.onDoneCallback onDoneCallback) {
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
    public void performAddFriend(User user) {
        User.GetFriends(new OnDoneListener<List<User>>() {
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

                List<DocumentReference> friendRefs = new ArrayList<>();

                for (User u : users) {
                    friendRefs.add(u.getFirebaseUser());
                }

                // add friend
                FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update("friends", friendRefs)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    onDoneCallback.onAddSuccess();
                                } else {
                                    onDoneCallback.onAddFailure("Failed to add friend");
                                }
                            }
                        });
            }

            @Override
            public void onFailure(String message) {
                onDoneCallback.onAddFailure(message);
            }
        });
    }
}
