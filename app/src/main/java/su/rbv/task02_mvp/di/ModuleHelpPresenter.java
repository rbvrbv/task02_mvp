package su.rbv.task02_mvp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.ui.main.help.HelpPresenter;

@Module
class ModuleHelpPresenter {

    ModuleHelpPresenter() {  }

    @Provides
    @Singleton
    HelpPresenter provideHelpPresenter() {
        return new HelpPresenter();
    }
}
