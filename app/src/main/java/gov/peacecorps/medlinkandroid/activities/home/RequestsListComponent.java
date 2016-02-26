package gov.peacecorps.medlinkandroid.activities.home;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(modules = RequestsListModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface RequestsListComponent {
    void inject(RequestsListActivity requestsListActivity);
}
