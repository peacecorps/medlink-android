package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;

@Module
public class UnsubmittedRequestModule {
    private RequestsListView requestsListView;

    public UnsubmittedRequestModule(RequestsListView requestsListView) {
        this.requestsListView = requestsListView;
    }

    @Provides
    @ActivityScope
    UnsubmittedRequestsPresenter provideUnsubmittedRequestsPresenter(DataManager dataManager){
        return new UnsubmittedRequestsPresenter(requestsListView, dataManager);
    }

    @Provides
    @ActivityScope
    UnsubmittedRequestsListAdapter provideRequestsListAdapter(DataManager dataManager){
        return new UnsubmittedRequestsListAdapter(requestsListView, dataManager);
    }
}
