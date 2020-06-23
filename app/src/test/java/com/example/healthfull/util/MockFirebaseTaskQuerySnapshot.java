package com.example.healthfull.util;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;

public class MockFirebaseTaskQuerySnapshot extends Task<QuerySnapshot> {

    private boolean complete;
    private boolean success;

    private QuerySnapshot querySnapshot;

    public MockFirebaseTaskQuerySnapshot(boolean complete, boolean success, QuerySnapshot querySnapshot) {
        this.complete = complete;
        this.success = success;
        this.querySnapshot = querySnapshot;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnCompleteListener(@NonNull OnCompleteListener<QuerySnapshot> onCompleteListener) {
        onCompleteListener.onComplete(this);
        return this;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Nullable
    @Override
    public QuerySnapshot getResult() {
        return querySnapshot;
    }

    @Nullable
    @Override
    public <X extends Throwable> QuerySnapshot getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnSuccessListener(@NonNull OnSuccessListener<? super QuerySnapshot> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super QuerySnapshot> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super QuerySnapshot> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<QuerySnapshot> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }
}
