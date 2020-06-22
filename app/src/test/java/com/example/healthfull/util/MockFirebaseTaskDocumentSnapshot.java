package com.example.healthfull.util;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.concurrent.Executor;

public class MockFirebaseTaskDocumentSnapshot extends Task<DocumentSnapshot> {

    private boolean complete;
    private boolean success;

    private DocumentSnapshot documentSnapshot;

    public MockFirebaseTaskDocumentSnapshot(boolean complete, boolean success, DocumentSnapshot documentSnapshot) {
        this.complete = complete;
        this.success = success;
        this.documentSnapshot = documentSnapshot;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnCompleteListener(@NonNull OnCompleteListener<DocumentSnapshot> onCompleteListener) {
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
    public DocumentSnapshot getResult() {
        return documentSnapshot;
    }

    @Nullable
    @Override
    public <X extends Throwable> DocumentSnapshot getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnSuccessListener(@NonNull OnSuccessListener<? super DocumentSnapshot> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super DocumentSnapshot> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super DocumentSnapshot> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<DocumentSnapshot> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }
}
