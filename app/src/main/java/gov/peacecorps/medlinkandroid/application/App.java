package gov.peacecorps.medlinkandroid.application;

import android.app.Application;

import com.karumi.dexter.Dexter;

import gov.peacecorps.medlinkandroid.BuildConfig;
import timber.log.Timber;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();

        initDexter();

        bootstrapDI();
    }

    private void initDexter() {
        Dexter.initialize(this);
    }

    private void initTimber() {
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void bootstrapDI() {
        appComponent = AppComponent.Initializer.initialize(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
