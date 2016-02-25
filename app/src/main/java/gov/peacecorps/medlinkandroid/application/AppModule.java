package gov.peacecorps.medlinkandroid.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.HmacSigner;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    AppSharedPreferences provideSharedPreferences(Context context){
        return new AppSharedPreferences(context);
    }

    @Provides
    @Singleton
    HmacSigner provideHmacSigner(AppSharedPreferences appSharedPreferences){
        return new HmacSigner(appSharedPreferences);
    }
}
