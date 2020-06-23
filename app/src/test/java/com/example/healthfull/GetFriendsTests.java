package com.example.healthfull;

import com.example.healthfull.profile.User;
import com.example.healthfull.util.MockFirebaseTaskDocumentSnapshot;
import com.example.healthfull.util.MockFirebaseTaskQuerySnapshot;
import com.example.healthfull.util.MockFirebaseTaskVoid;
import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({
        FirebaseAuth.class,
        FirebaseFirestore.class
})

public class GetFriendsTests {

    CollectionReference collectionReference;
    DocumentReference documentReference;
    Task taskReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentSnapshot documentSnapshot;
    QuerySnapshot querySnapshot;
    Query query;

    Task mockDocSnapshotTask;
    Task mockQuerySnapshotTask;

    @Before
    public void before() {
        collectionReference = PowerMockito.mock(CollectionReference.class);
        documentReference = PowerMockito.mock(DocumentReference.class);
        taskReference = PowerMockito.mock(Task.class);
        firebaseUser = PowerMockito.mock(FirebaseUser.class);
        firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
        firebaseFirestore = PowerMockito.mock(FirebaseFirestore.class);
        documentSnapshot = PowerMockito.mock(DocumentSnapshot.class);
        querySnapshot = PowerMockito.mock(QuerySnapshot.class);
        query = PowerMockito.mock(Query.class);

        PowerMockito.mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        when(firebaseAuth.getCurrentUser().getUid()).thenReturn("vCRMif3c2IhkirRb4mHVxAcnyeA3");

        PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(firebaseFirestore);

        when(firebaseFirestore.collection(any())).thenReturn(collectionReference);
        when(firebaseFirestore.collection(any()).document(any())).thenReturn(documentReference);

        mockDocSnapshotTask = new MockFirebaseTaskDocumentSnapshot(true, true, documentSnapshot);

        mockQuerySnapshotTask = new MockFirebaseTaskQuerySnapshot(true, true, querySnapshot);

        List<DocumentSnapshot> queryDocuments = new ArrayList<>();
        queryDocuments.add(documentSnapshot);

        Map<String, Object> documentSnapshotData = new HashMap<>();
        documentSnapshotData.put("name", "Test User");

        when(documentReference.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(any())).thenReturn(documentReference);
        when(collectionReference.document()).thenReturn(documentReference);
        when(collectionReference.whereEqualTo((FieldPath) any(), any())).thenReturn(query);
        when(collectionReference.whereEqualTo((String) any(), any())).thenReturn(query);
        when(documentReference.set(any())).thenReturn(new MockFirebaseTaskVoid(true, true));
        when(documentReference.get()).thenReturn(mockDocSnapshotTask);
        when(documentReference.get(any())).thenReturn(mockDocSnapshotTask);
        when(documentSnapshot.getData()).thenReturn(documentSnapshotData);
        when(query.get()).thenReturn(mockQuerySnapshotTask);
        when(querySnapshot.getDocuments()).thenReturn(queryDocuments);
    }

    @Test
    public void getUserFriendsTest() {
        CountDownLatch latch = new CountDownLatch(1);

        final boolean[] success = { false };

        User.GetFriends(new OnDoneListener<List<User>>() {
            @Override
            public void onSuccess(List<User> object) {
                success[0] = true;
                latch.countDown();
            }

            @Override
            public void onFailure(String message) {
                success[0] = false;
                latch.countDown();
            }
        });

        try {
            latch.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // continue
        }

        assertTrue("Failed to get friends", success[0]);
    }

    @Test
    public void getUser() {
        String email = "test@test.com";

        CountDownLatch latch = new CountDownLatch(1);

        final boolean[] success = { false };

        User.GetUser(email, new OnDoneListener<User>() {
            @Override
            public void onSuccess(User object) {
                success[0] = true;
                latch.countDown();
            }

            @Override
            public void onFailure(String message) {
                success[0] = false;
                latch.countDown();
            }
        });

        try {
            latch.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // continue
        }

        assertTrue("Failed to get user by email", success[0]);
    }

}
