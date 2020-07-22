package su.rbv.task02_mvp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.data.ConfSharedPreferences;
import su.rbv.task02_mvp.data.IConf;

@Module
public class ModuleConfSharedPreferences {

    private final Context ctx;

    public ModuleConfSharedPreferences(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    IConf provideConfSharedPreferences() {
        return new ConfSharedPreferences(ctx);
    }
}
