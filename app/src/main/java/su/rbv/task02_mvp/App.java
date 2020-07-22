package su.rbv.task02_mvp;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

import su.rbv.task02_mvp.di.AppComponent;
import su.rbv.task02_mvp.di.DaggerAppComponent;
import su.rbv.task02_mvp.di.ModuleConfSharedPreferences;
import su.rbv.task02_mvp.di.ModuleJSONDatabase;
import su.rbv.task02_mvp.di.ModuleJSONLoader;
import su.rbv.task02_mvp.di.ModuleWeb;

public class App extends Application {

    private static final String BASE_URL ="https://mentoring-35da2.firebaseio.com/";

    private static AppComponent component;

    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);

        component = DaggerAppComponent.builder()
                .moduleJSONLoader(new ModuleJSONLoader(getApplicationContext()))
                .moduleConfSharedPreferences(new ModuleConfSharedPreferences(getApplicationContext()))
                .moduleJSONDatabase(new ModuleJSONDatabase(getApplicationContext()))
                .moduleWeb(new ModuleWeb(BASE_URL))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

}
