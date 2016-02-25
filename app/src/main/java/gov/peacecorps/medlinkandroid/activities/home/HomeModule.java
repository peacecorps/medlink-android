package gov.peacecorps.medlinkandroid.activities.home;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Module
public class HomeModule {

    private HomeView homeView;

    public HomeModule(HomeView homeView) {
        this.homeView = homeView;
    }

    @Provides
    @ActivityScope
    HomePresenter providePresenter(){
        return new HomePresenter(homeView);
    }
}
