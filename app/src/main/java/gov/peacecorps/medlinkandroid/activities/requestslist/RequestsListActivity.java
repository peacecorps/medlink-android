package gov.peacecorps.medlinkandroid.activities.requestslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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
import gov.peacecorps.medlinkandroid.activities.createrequest.CreateRequestActivity;
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

    @Bind(R.id.newOrderFab)
    FloatingActionButton newOrderFab;

    @Bind(R.id.orderHistoryFab)
    FloatingActionButton orderHistoryFab;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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

        orderHistoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open activity that shows order history
                Snackbar.make(view, R.string.not_yet_implemented, Snackbar.LENGTH_SHORT).show();
            }
        });

        newOrderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateRequestsActivity();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestsListPresenter.getRequestsList();
            }
        });
    }

    private void goToCreateRequestsActivity() {
        Intent intent = new Intent(this, CreateRequestActivity.class);
        startActivity(intent);
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
        swipeRefreshLayout.setRefreshing(false);
    }
}
