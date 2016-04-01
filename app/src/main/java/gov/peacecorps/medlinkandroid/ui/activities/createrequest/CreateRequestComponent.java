package gov.peacecorps.medlinkandroid.ui.activities.createrequest;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(modules = CreateRequestModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface CreateRequestComponent {
    void inject(CreateRequestActivity createRequestActivity);
}
