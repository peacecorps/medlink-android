package gov.peacecorps.medlinkandroid.application;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.RestModule;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Component(modules = {AppModule.class, RestModule.class})
@Singleton
public interface AppComponent {

    void inject(Application application);

    API exposeAPI();

    AppSharedPreferences exposeSharedPreferences();

    DataManager exposeDataManager();

    final class Initializer {
        public static AppComponent initialize(Application application){
            AppComponent appComponent = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(application))
                    .restModule(new RestModule())
                    .build();
            appComponent.inject(application);

            return appComponent;
        }
    }
}
