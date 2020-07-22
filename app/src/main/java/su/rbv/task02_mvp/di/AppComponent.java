package su.rbv.task02_mvp.di;

import javax.inject.Singleton;

import dagger.Component;
import su.rbv.task02_mvp.ui.auth.AuthActivity;
import su.rbv.task02_mvp.data.ConfInteract;
import su.rbv.task02_mvp.ui.main.profile.EditMainPictureFragment;
import su.rbv.task02_mvp.ui.main.event.EventDetailFragment;
import su.rbv.task02_mvp.ui.main.event.EventDetailPresenter;
import su.rbv.task02_mvp.ui.main.help.HelpFragment;
import su.rbv.task02_mvp.ui.main.help.HelpPresenter;
import su.rbv.task02_mvp.data.JSONLoader;
import su.rbv.task02_mvp.ui.main.MainActivity;
import su.rbv.task02_mvp.ui.main.MainPresenter;
import su.rbv.task02_mvp.ui.main.news.NewsFilterFragment;
import su.rbv.task02_mvp.ui.main.news.NewsFilterPresenter;
import su.rbv.task02_mvp.ui.main.news.NewsFragment;
import su.rbv.task02_mvp.ui.main.news.NewsPresenter;
import su.rbv.task02_mvp.ui.main.profile.ProfileFragment;
import su.rbv.task02_mvp.ui.main.profile.ProfilePresenter;
import su.rbv.task02_mvp.ui.main.search.SearchFragment;
import su.rbv.task02_mvp.ui.main.search.SearchPresenter;
import su.rbv.task02_mvp.ui.SplashActivity;

@Component ( modules = {
        ModuleNewsPresenter.class,
        ModuleHelpPresenter.class,
        ModuleSearchPresenter.class,
        ModuleConfSharedPreferences.class,
        ModuleJSONLoader.class,
        ModuleJSONDatabase.class,
        ModuleWeb.class,
        ModuleConfInteract.class
} )

@Singleton
public interface AppComponent {
    void inject(AuthActivity authActivity);
    void inject(MainActivity mainActivity);
    void inject(SplashActivity splashActivity);
    void inject(ProfileFragment profileFragment);
    void inject(HelpFragment helpFragment);
    void inject(NewsFragment newsFragment);
    void inject(NewsFilterFragment newsFilterFragment);
    void inject(EventDetailFragment eventDetailFragment);
    void inject(SearchFragment searchFragment);
    void inject(EditMainPictureFragment editMainPictureFragment);
    void inject(MainPresenter mainPresenter);
    void inject(HelpPresenter helpPresenter);
    void inject(NewsPresenter newsPresenter);
    void inject(NewsFilterPresenter newsFilterPresenter);
    void inject(EventDetailPresenter eventDetailPresenter);
    void inject(SearchPresenter searchPresenter);
    void inject(ProfilePresenter profilePresenter);
    void inject(JSONLoader jsonLoader);
    void inject(ConfInteract confInteract);
}
