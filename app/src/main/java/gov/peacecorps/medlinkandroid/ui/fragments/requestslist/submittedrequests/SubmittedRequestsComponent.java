package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.requesthistory.RequestsHistoryFragment;

@Component(modules = SubmittedRequestsModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface SubmittedRequestsComponent {
    void inject(SubmittedRequestsFragment submittedRequestsFragment);
    void inject(RequestsHistoryFragment requestsHistoryFragment);
}
