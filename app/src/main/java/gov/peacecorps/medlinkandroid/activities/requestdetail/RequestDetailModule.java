package gov.peacecorps.medlinkandroid.activities.requestdetail;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Module
public class RequestDetailModule {

    private RequestDetailView requestDetailView;

    public RequestDetailModule(RequestDetailView requestDetailView) {
        this.requestDetailView = requestDetailView;
    }

    @Provides
    @ActivityScope
    SupplyListAdapter provideSupplyListAdapter(AppSharedPreferences appSharedPreferences, API api){
        return new SupplyListAdapter(requestDetailView, appSharedPreferences, api);
    }
}
