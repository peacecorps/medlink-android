package gov.peacecorps.medlinkandroid.application;

import android.app.Application;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        bootstrapDI();
    }

    private void bootstrapDI() {
        appComponent = AppComponent.Initializer.initialize(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }



}
