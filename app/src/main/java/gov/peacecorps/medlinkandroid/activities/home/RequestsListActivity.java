package gov.peacecorps.medlinkandroid.activities.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.application.AppComponent;

public class RequestsListActivity extends BaseActivity implements RequestsListView {

    @Inject
    RequestsListPresenter requestsListPresenter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRequestsListComponent
                .builder()
                .appComponent(appComponent)
                .requestsListModule(new RequestsListModule(this))
                .build()
                .inject(this);
    }

    //TODO: create recycler view for the requests list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open activity to make a new request
            }
        });
    }
}
