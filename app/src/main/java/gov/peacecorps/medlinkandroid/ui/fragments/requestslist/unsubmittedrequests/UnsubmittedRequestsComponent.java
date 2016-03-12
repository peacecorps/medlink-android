package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(modules = UnsubmittedRequestModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface UnsubmittedRequestsComponent {
    void inject(UnsubmittedRequestsFragment unsubmittedRequestsFragment);
}
