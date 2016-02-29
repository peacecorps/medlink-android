package gov.peacecorps.medlinkandroid.activities.requestslist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;

public class RequestsListActivity extends BaseActivity implements RequestsListView {

    @Inject
    RequestsListPresenter requestsListPresenter;

    @Inject
    RequestsListAdapter requestsListAdapter;

    @Inject
    AppSharedPreferences appSharedPreferences;

    @Bind(R.id.requestsListRv)
    RecyclerView requestsListRv;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRequestsListComponent
                .builder()
                .appComponent(appComponent)
                .requestsListModule(new RequestsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);

        ButterKnife.bind(this);

        initRequestListRecyclerView();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newOrderFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open activity to make a new request
            }
        });
    }

    private void initRequestListRecyclerView() {
        requestsListRv.setLayoutManager(new LinearLayoutManager(this));
        requestsListRv.setAdapter(requestsListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestsListPresenter.getRequestsList();
    }

    @Override
    public void displayRequests(List<Request> requests) {
        requestsListAdapter.updateRequests(requests);
    }
}
