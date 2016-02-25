package gov.peacecorps.medlinkandroid.activities.login;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(modules = LoginModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
