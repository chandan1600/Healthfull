package com.example.healthfull.profile;

import androidx.annotation.NonNull;

import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class User {
    private boolean loaded;
    private DocumentReference firebaseUser;
    private String name;
    private List<DocumentReference> friends;
    private List<DocumentReference> clients;
    private DocumentReference trainer;

    public User() {
        this.loaded = false;
        this.firebaseUser = null;
        this.name = "";
        this.friends = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.trainer = null;
    }

    public User(DocumentReference firebaseUser) {
        this.loaded = false;
        this.firebaseUser = firebaseUser;
        this.name = "";
        this.friends = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.trainer = null;
    }

    public DocumentReference getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(DocumentReference firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentReference> getFriends() {
        return friends;
    }

    public void setFriends(List<DocumentReference> friends) {
        this.friends = friends;
    }

    public List<DocumentReference> getClients() {
        return clients;
    }

    public void setClients(List<DocumentReference> clients) {
        this.clients = clients;
    }

    public DocumentReference getTrainer() {
        return trainer;
    }

    public void setTrainer(DocumentReference trainer) {
        this.trainer = trainer;
    }

    public static void GetUser(DocumentReference userReference, OnDoneListener<User> onDoneListener) {
        userReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            List<DocumentReference> friendRefs = (ArrayList<DocumentReference>)doc.getData().get("friends");
                            List<DocumentReference> clientRefs = (ArrayList<DocumentReference>)doc.getData().get("clients");

                            User user = new User();
                            user.setFirebaseUser(doc.getReference());
                            user.setName(doc.getData().get("name").toString());
                            user.setFriends(friendRefs);
                            user.setClients(clientRefs);
                            user.setTrainer((DocumentReference) doc.getData().get("trainer"));

                            onDoneListener.onSuccess(user);
                        } else {
                            onDoneListener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    public static void GetUser(String email, OnDoneListener<User> onDoneListener) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().getDocuments().isEmpty()) {
                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                    List<DocumentReference> friendRefs = (ArrayList<DocumentReference>)doc.getData().get("friends");
                    List<DocumentReference> clientRefs = (ArrayList<DocumentReference>)doc.getData().get("clients");

                    User user = new User();
                    user.setFirebaseUser(doc.getReference());
                    user.setName(doc.getData().get("name").toString());
                    user.setFriends(friendRefs);
                    user.setClients(clientRefs);
                    user.setTrainer((DocumentReference) doc.getData().get("trainer"));

                    onDoneListener.onSuccess(user);
                } else {
                    onDoneListener.onFailure("No Results");
                }
            }
        });
    }

    public static void GetFriends(OnDoneListener<List<User>> onDoneListener) {
        // Current user reference
        DocumentReference currentUserReference = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        currentUserReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    private int requests = 0;
                    private void checkAllDone(List<User> friends) {
                        if (requests == 0) {
                            onDoneListener.onSuccess(friends);
                        }
                    }

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            List<DocumentReference> friendRefs = (List<DocumentReference>)doc.getData().get("friends");

                            List<User> friends = new ArrayList<>();

                            if (friendRefs == null || friendRefs.isEmpty()) {
                                onDoneListener.onSuccess(new ArrayList<>());
                                return;
                            }
                            for (DocumentReference friendRef : friendRefs) {
                                requests++;
                                friendRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = task.getResult();

                                            if (doc.exists()) {

                                                List<DocumentReference> friendsFriendRefs = (List<DocumentReference>) doc.getData().get("friends");
                                                if (friendsFriendRefs != null) {

                                                    // check if both have the friend entry
                                                    for (DocumentReference ref : friendsFriendRefs) {
                                                        if (ref.equals(currentUserReference)) {
                                                            User friend = new User();

                                                            friend.setName(doc.getData().get("name").toString());
                                                            friend.setFirebaseUser(doc.getReference());
                                                            friend.setFriends(friendsFriendRefs);

                                                            friends.add(friend);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            // skip this friend
                                        }
                                        requests--;
                                        checkAllDone(friends);
                                    }
                                });
                            }
                        } else {
                            onDoneListener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    public static void GetClients(String trainerId, OnDoneListener<List<User>> onDoneListener) {
        // User reference
        DocumentReference trainerReference = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(trainerId);

        trainerReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    private int requests = 0;
                    private void checkAllDone(List<User> clients) {
                        if (requests == 0) {
                            onDoneListener.onSuccess(clients);
                        }
                    }

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            List<DocumentReference> clientRefs = (List<DocumentReference>) doc.getData().get("clients");

                            List<User> clients = new ArrayList<>();

                            if (clientRefs == null || clientRefs.isEmpty()) {
                                // return empty list of clients
                                onDoneListener.onSuccess(new ArrayList<>());
                                return;
                            }
                            for (DocumentReference clientRef : clientRefs) {
                                requests++;
                                clientRef
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot doc = task.getResult();

                                                    if (doc.exists()) {
                                                        // check if the client has added the trainer
                                                        DocumentReference clientsTrainerRef = (DocumentReference) doc.getData().get("trainer");
                                                        if (clientsTrainerRef != null) {
                                                            // check if both have the friend entry
                                                            if (clientsTrainerRef.equals(trainerReference)) {
                                                                User client = new User();

                                                                client.setName(doc.getData().get("name").toString());
                                                                client.setFirebaseUser(doc.getReference());
                                                                client.setTrainer(trainerReference);

                                                                clients.add(client);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    // skip this friend
                                                }
                                                requests--;
                                                checkAllDone(clients);
                                            }
                                        });
                            }
                        } else {
                            onDoneListener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    public static void GetClients(User trainer, OnDoneListener<List<User>> onDoneListener) {
        GetClients(trainer.getFirebaseUser().getId(), onDoneListener);
    }

        public static void GetUsers(String username, OnDoneListener<List<User>> onDoneListener) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .whereEqualTo("name", username)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot q : task.getResult()) {
                        User user = new User();
                        user.setName(q.getData().get("name").toString());
                    }
                } else {
                    onDoneListener.onFailure(task.getException().getMessage());
                }
            }
        });
    }
}
