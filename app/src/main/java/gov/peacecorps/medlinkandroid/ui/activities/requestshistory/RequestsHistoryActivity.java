package gov.peacecorps.medlinkandroid.ui.activities.requestshistory;

import android.os.Bundle;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.requesthistory.RequestsHistoryFragment;

public class RequestsHistoryActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RequestsHistoryFragment()).commit();
    }
}
