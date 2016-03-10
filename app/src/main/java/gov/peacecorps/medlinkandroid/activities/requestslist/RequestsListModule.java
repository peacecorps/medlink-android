package gov.peacecorps.medlinkandroid.activities.requestslist;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Module
public class RequestsListModule {

    private RequestsListView requestsListView;

    public RequestsListModule(RequestsListView requestsListView) {
        this.requestsListView = requestsListView;
    }

    @Provides
    @ActivityScope
    RequestsListPresenter providePresenter(API api, DataManager dataManager){
        return new RequestsListPresenter(requestsListView, api, dataManager);
    }

    @Provides
    @ActivityScope
    RequestsListAdapter provideRequestsListAdapter(DataManager dataManager){
        return new RequestsListAdapter(requestsListView, dataManager);
    }
}
