package gov.peacecorps.medlinkandroid.ui.activities.login;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.service.API;

@Module
public class LoginModule {

    private LoginView loginView;

    public LoginModule(LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @ActivityScope
    LoginPresenter provideLoginPresenter(API api, DataManager dataManager){
        return new LoginPresenter(loginView, api, dataManager);
    }
}
