package gov.peacecorps.medlinkandroid.activities.home;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(modules = HomeModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
