package com.example.healthfull;


import com.example.healthfull.register.RegisterContract;
import com.example.healthfull.register.RegisterPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.Date;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({
        FirebaseAuth.class,
        FirebaseFirestore.class
})

public class RegisterUnitTests {

    @Before
    public void before() {

    }

    @Test
    public void registrationTest() {
        RegisterPresenter presenter = new RegisterPresenter(new RegisterContract.View() {
            @Override
            public void onRegisterSuccess(String message) {

            }

            @Override
            public void onRegisterFailure(String message) {

            }

            @Override
            public void setInputEnabled(boolean enabled) {

            }
        });

        presenter.submitDetails("Test User", new Date());
    }

}
