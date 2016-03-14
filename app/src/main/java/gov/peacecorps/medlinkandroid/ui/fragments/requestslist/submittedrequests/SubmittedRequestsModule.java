package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.service.API;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.requesthistory.RequestsHistoryListAdapter;

@Module
public class SubmittedRequestsModule {
    private RequestsListView requestsListView;

    public SubmittedRequestsModule(RequestsListView requestsListView) {
        this.requestsListView = requestsListView;
    }


    @Provides
    @ActivityScope
    SubmittedRequestsPresenter providePresenter(API api, DataManager dataManager){
        return new SubmittedRequestsPresenter(requestsListView, api, dataManager);
    }

    @Provides
    @ActivityScope
    SubmittedRequestsListAdapter provideSubmittedRequestsListAdapter(DataManager dataManager){
        return new SubmittedRequestsListAdapter(requestsListView, dataManager);
    }

    @Provides
    @ActivityScope
    RequestsHistoryListAdapter provideRequestsHistoryListAdapter(DataManager dataManager){
        return new RequestsHistoryListAdapter(requestsListView, dataManager);
    }
}
