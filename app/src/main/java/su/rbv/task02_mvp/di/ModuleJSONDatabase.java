package su.rbv.task02_mvp.di;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.data.JSONDatabase;

@Module
public class ModuleJSONDatabase {

    private static final String JSON_DATABASE = "JSON_DATABASE";

    private final Context ctx;

    public ModuleJSONDatabase(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    JSONDatabase provideJSONDB() {
        return Room.databaseBuilder(ctx, JSONDatabase.class, JSON_DATABASE)
                .fallbackToDestructiveMigration()
                .build();
    }
}
