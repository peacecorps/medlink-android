package gov.peacecorps.medlinkandroid.activities.createrequest;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Module
public class CreateRequestModule {
    private CreateRequestView createRequestView;

    public CreateRequestModule(CreateRequestView createRequestView) {
        this.createRequestView = createRequestView;
    }

    @Provides
    @ActivityScope
    CreateRequestPresenter provideCreateRequestPresenter(API api){
        return new CreateRequestPresenter(createRequestView, api);
    }

    @Provides
    @ActivityScope
    SupplyListAdapter provideSupplyListAdapter(AppSharedPreferences appSharedPreferences){
        return new SupplyListAdapter(createRequestView, appSharedPreferences);
    }
}
