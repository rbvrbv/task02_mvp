package su.rbv.task02_mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONDatabase;
import su.rbv.task02_mvp.ui.auth.AuthActivity;

public class SplashActivity extends AppCompatActivity {

    @Inject
    JSONDatabase jsonDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final int SPLASH_SCREEN_DELAY = 1000;

        App.getComponent().inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Thread(() -> {
            jsonDB.jsonCategoryDao().flush();
            jsonDB.jsonEventDao().flush();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(this, AuthActivity.class);
                //Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }, SPLASH_SCREEN_DELAY);

        }).start();

    }
}
