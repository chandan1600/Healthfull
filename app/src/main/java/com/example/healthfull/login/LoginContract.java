package com.example.healthfull.login;

public interface LoginContract {
    interface View {
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
        void setInputEnabled(boolean enabled);
    }

    interface Presenter {
        void login(String email, String password);
    }

    interface Interactor {
        /**
         * Attempt to login or create a user in Firebase
         * @param email User's email address
         * @param password User's account password
         */
        void performFirebaseLogin(String email, String password);
    }

    interface onLoginListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
