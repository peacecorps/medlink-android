package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.fragments.BaseFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;

public class SubmittedRequestsFragment extends BaseFragment implements RequestsListView {

    @Inject
    SubmittedRequestsPresenter submittedRequestsPresenter;

    @Inject
    SubmittedRequestsListAdapter submittedRequestsListAdapter;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.requestsListRv)
    RecyclerView requestsListRv;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerSubmittedRequestsComponent
                .builder()
                .appComponent(appComponent)
                .submittedRequestsModule(new SubmittedRequestsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_submitted_or_unsubmitted_requests, container, false);

        ButterKnife.bind(this, view);

        initRequestListRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                submittedRequestsPresenter.getSupplies();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        submittedRequestsPresenter.getSupplies();
    }

    private void initRequestListRecyclerView() {
        requestsListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestsListRv.setAdapter(submittedRequestsListAdapter);
    }

    @Override
    public void displayRequests(List<RequestListItem> requests) {
        submittedRequestsListAdapter.updateSubmittedRequests(requests);
    }

    @Override
    public void clearSwipeAnimation() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
