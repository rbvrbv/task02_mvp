package su.rbv.task02_mvp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.data.IJSONLoader;
import su.rbv.task02_mvp.data.JSONLoader;

@Module
public class ModuleJSONLoader {

    private final Context ctx;

    public ModuleJSONLoader(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    IJSONLoader provideJSONLoader() {
        return new JSONLoader(ctx);
    }
}
