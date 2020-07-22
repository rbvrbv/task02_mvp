package su.rbv.task02_mvp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding3.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import su.rbv.task02_mvp.ui.main.MainActivity;
import su.rbv.task02_mvp.R;

public class AuthActivity extends MvpAppCompatActivity implements IAuthView {

    private EditText authEmail;
    private EditText authPassword;
    private View authButton;

    private Disposable authButtonDisposable;

    @InjectPresenter
    AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);

        authEmail = findViewById(R.id.auth_email);
        authPassword = findViewById(R.id.auth_password);
        authButton = findViewById(R.id.auth_button);

        authButtonDisposable = Observable.merge(
                RxTextView.textChanges(authEmail),
                RxTextView.textChanges(authPassword)
        ).subscribe(t -> authPresenter.onTextFieldsChange(authEmail.getText().toString(), authPassword.getText().toString()));

    }

    @Override
    protected void onDestroy() {
        if (authButtonDisposable != null && !authButtonDisposable.isDisposed()) {
            authButtonDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void enableLoginButton() {
        authButton.setEnabled(true);
    }

    @Override
    public void disableLoginButton() {
        authButton.setEnabled(false);
    }

    @Override
    public void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        close();
    }

    @Override
    public void close() {
        finish();
    }

    //======== XML click handlers ==========================================
    public void onAuthClick(View v) {
        authPresenter.onAuthClick(authEmail.getText().toString(), authPassword.getText().toString());
    }

    public void onBackClick(View v) {
        authPresenter.onBackClick();
    }

}
