package gov.peacecorps.medlinkandroid.activities.home;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Module
public class RequestsListModule {

    private RequestsListView requestsListView;

    public RequestsListModule(RequestsListView requestsListView) {
        this.requestsListView = requestsListView;
    }

    @Provides
    @ActivityScope
    RequestsListPresenter providePresenter(){
        return new RequestsListPresenter(requestsListView);
    }
}
