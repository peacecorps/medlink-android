package gov.peacecorps.medlinkandroid.ui.activities.createrequest;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Module
public class CreateRequestModule {
    private CreateRequestView createRequestView;

    public CreateRequestModule(CreateRequestView createRequestView) {
        this.createRequestView = createRequestView;
    }

    @Provides
    @ActivityScope
    CreateRequestPresenter provideCreateRequestPresenter(API api, DataManager dataManager){
        return new CreateRequestPresenter(createRequestView, api, dataManager);
    }

    @Provides
    @ActivityScope
    SupplyListAdapter provideSupplyListAdapter(DataManager dataManager){
        return new SupplyListAdapter(createRequestView, dataManager);
    }
}
