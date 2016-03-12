package gov.peacecorps.medlinkandroid.ui.activities;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(dependencies = AppComponent.class)
@ActivityScope
public interface BaseComponent {
    void inject(BaseActivity baseActivity);
}
