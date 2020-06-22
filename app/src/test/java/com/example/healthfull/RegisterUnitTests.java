package com.example.healthfull;


import android.text.TextUtils;

import com.example.healthfull.register.RegisterContract;
import com.example.healthfull.register.RegisterPresenter;
import com.example.healthfull.util.MockFirebaseTaskVoid;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({
        FirebaseAuth.class,
        FirebaseFirestore.class,
        TextUtils.class
})

public class RegisterUnitTests {

    CollectionReference collectionReference;
    DocumentReference documentReference;
    Task taskReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Before
    public void before() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                return !(a != null && a.length() > 0);
            }
        });

        collectionReference = PowerMockito.mock(CollectionReference.class);
        documentReference = PowerMockito.mock(DocumentReference.class);
        taskReference = PowerMockito.mock(Task.class);
        firebaseUser = PowerMockito.mock(FirebaseUser.class);
        firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
        firebaseFirestore = PowerMockito.mock(FirebaseFirestore.class);

        PowerMockito.mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        when(firebaseAuth.getCurrentUser().getUid()).thenReturn("vCRMif3c2IhkirRb4mHVxAcnyeA3");

        PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(firebaseFirestore);

        when(firebaseFirestore.collection(any())).thenReturn(collectionReference);
        when(firebaseFirestore.collection(any()).document(any())).thenReturn(documentReference);

        when(documentReference.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(any())).thenReturn(documentReference);
        when(collectionReference.document()).thenReturn(documentReference);
        when(documentReference.set(any())).thenReturn(new MockFirebaseTaskVoid(true, true));
    }

    @Test
    public void registrationTest() {
        CountDownLatch latch = new CountDownLatch(1);

        final boolean[] registered  = { false };

        RegisterPresenter presenter = new RegisterPresenter(new RegisterContract.View() {
            @Override
            public void onRegisterSuccess(String message) {
                registered[0] = true;
                latch.countDown();
            }

            @Override
            public void onRegisterFailure(String message) {
                registered[0] = false;
                latch.countDown();
            }

            @Override
            public void setInputEnabled(boolean enabled) {
                // ignore
            }
        });

        presenter.submitDetails("Test User", new Date());

        try {
            latch.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // continue
        }

        assertTrue("Registration failed", registered[0]);
    }

}
