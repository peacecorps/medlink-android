package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Module
public class SubmittedRequestsModule {
    private SubmittedRequestsView submittedRequestsView;

    public SubmittedRequestsModule(SubmittedRequestsView submittedRequestsView) {
        this.submittedRequestsView = submittedRequestsView;
    }

    @Provides
    @ActivityScope
    SubmittedRequestsPresenter providePresenter(API api, DataManager dataManager){
        return new SubmittedRequestsPresenter(submittedRequestsView, api, dataManager);
    }

    @Provides
    @ActivityScope
    SubmittedRequestsListAdapter provideRequestsListAdapter(DataManager dataManager){
        return new SubmittedRequestsListAdapter(submittedRequestsView, dataManager);
    }
}
