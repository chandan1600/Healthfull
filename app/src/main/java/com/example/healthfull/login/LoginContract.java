package com.example.healthfull.login;

public interface LoginContract {
    interface View {
        void onLoginSuccess();
        void onLoginFailure(String message);
        void setInputEnabled(boolean enabled);
    }

    interface Presenter {
        void login(String email, String password);
    }

    interface Interactor {
        void performFirebaseLogin(String email, String password);
    }

    interface onLoginListener {
        void onSuccess();
        void onFailure(String message);
    }
}
