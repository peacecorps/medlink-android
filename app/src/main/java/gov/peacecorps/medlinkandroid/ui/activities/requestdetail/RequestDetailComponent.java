package gov.peacecorps.medlinkandroid.ui.activities.requestdetail;

import dagger.Component;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.di.annotation.ActivityScope;

@Component(modules = RequestDetailModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface RequestDetailComponent {
    void inject(RequestDetailActivity requestDetailActivity);
}
