package gov.peacecorps.medlinkandroid.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.rest.service.API;
import gov.peacecorps.medlinkandroid.rest.RestModule;

@Component(modules = {AppModule.class, RestModule.class})
@Singleton
public interface AppComponent {

    void inject(Application application);

    Context exposeContext();

    API exposeAPI();

    AppSharedPreferences exposeSharedPreferences();

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
