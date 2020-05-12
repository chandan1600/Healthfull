package com.example.healthfull.login;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.onLoginListener {

    private LoginContract.View view;
    private LoginContract.Interactor loginInteractor;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.loginInteractor = new LoginInteractor(this);
    }

    public void login(String email, String password) {
        view.setInputEnabled(false);
        loginInteractor.performFirebaseLogin(email, password);
    }

    @Override
    public void onSuccess(String message) {
        view.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        view.setInputEnabled(true);
        view.onLoginFailure(message);
    }
}
