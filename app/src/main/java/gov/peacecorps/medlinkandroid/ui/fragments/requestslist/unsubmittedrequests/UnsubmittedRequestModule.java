package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;

@Module
public class UnsubmittedRequestModule {
    private UnsubmittedRequestsView unsubmittedRequestsView;

    public UnsubmittedRequestModule(UnsubmittedRequestsView unsubmittedRequestsView) {
        this.unsubmittedRequestsView = unsubmittedRequestsView;
    }

    @Provides
    @ActivityScope
    UnsubmittedRequestsListAdapter provideRequestsListAdapter(DataManager dataManager){
        return new UnsubmittedRequestsListAdapter(unsubmittedRequestsView, dataManager);
    }
}
