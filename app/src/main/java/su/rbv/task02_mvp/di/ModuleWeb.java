package su.rbv.task02_mvp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.rbv.task02_mvp.network.Web;

@Module
public class ModuleWeb {

    private final String baseURL;

    public ModuleWeb(String baseURL) {
        this.baseURL = baseURL;
    }

    @Provides
    @Singleton
    Web provideWeb() {
        return new Web(baseURL);
    }
}
