package su.rbv.task02_mvp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.data.ConfInteract;

@Module
class ModuleConfInteract {

    @Provides
    @Singleton
    ConfInteract provideConfInteract() {
        return new ConfInteract();
    }
}
