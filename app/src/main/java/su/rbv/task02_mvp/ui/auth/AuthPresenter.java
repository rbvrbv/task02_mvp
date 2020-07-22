package su.rbv.task02_mvp.ui.auth;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
class AuthPresenter extends MvpPresenter<IAuthView> {

    private static int EMAIL_LENGTH_THRESHOLD = 6;
    private static int PASSWORD_LENGTH_THRESHOLD = 6;

    AuthPresenter() { }

    void onTextFieldsChange(String email, String password) {
        if (email.length() >= EMAIL_LENGTH_THRESHOLD && password.length() >= PASSWORD_LENGTH_THRESHOLD) {
            getViewState().enableLoginButton();
        } else {
            getViewState().disableLoginButton();
        }
    }

    void onAuthClick(String email, String password) {
        getViewState().launchMainActivity();
    }

    void onBackClick() {
        getViewState().close();
    }

}
