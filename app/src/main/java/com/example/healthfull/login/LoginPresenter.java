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

//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    FirebaseUser user = auth.getCurrentUser();
//
//                } else {
//
//                    view.setInputEnabled(true);
//                }
//            }
//        });
    }

    @Override
    public void onSuccess() {
        view.onLoginSuccess();
    }

    @Override
    public void onFailure(String message) {
        view.setInputEnabled(true);
        view.onLoginFailure(message);
    }
}
