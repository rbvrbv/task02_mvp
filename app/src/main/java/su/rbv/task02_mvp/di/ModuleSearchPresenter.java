package su.rbv.task02_mvp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.ui.main.search.SearchPresenter;

@Module
class ModuleSearchPresenter {

    ModuleSearchPresenter() {  }

    @Provides
    @Singleton
    SearchPresenter provideSearchPresenter() {
        return new SearchPresenter();
    }
}
