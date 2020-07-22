package su.rbv.task02_mvp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.ui.main.news.NewsPresenter;

@Module
class ModuleNewsPresenter {

    ModuleNewsPresenter() {  }

    @Provides
    @Singleton
    NewsPresenter provideNewsPresenter() {
        return new NewsPresenter();
    }
}
