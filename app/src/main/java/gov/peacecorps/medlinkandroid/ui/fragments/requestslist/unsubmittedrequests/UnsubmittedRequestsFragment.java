package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

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
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class UnsubmittedRequestsFragment extends BaseFragment implements RequestsListView {

    @Inject
    UnsubmittedRequestsPresenter unsubmittedRequestsPresenter;

    @Inject
    UnsubmittedRequestsListAdapter unsubmittedRequestsListAdapter;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.requestsListRv)
    RecyclerView requestsListRv;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerUnsubmittedRequestsComponent
                .builder()
                .appComponent(appComponent)
                .unsubmittedRequestModule(new UnsubmittedRequestModule(this))
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
                unsubmittedRequestsPresenter.getUnsubmittedRequests();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        unsubmittedRequestsPresenter.getUnsubmittedRequests();
    }

    private void initRequestListRecyclerView() {
        requestsListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestsListRv.setAdapter(unsubmittedRequestsListAdapter);
    }

    @Override
    public void displayRequests(List<RequestListItem> requests) {
        unsubmittedRequestsListAdapter.updateUnsubmittedRequests(requests);
    }

    @Override
    public void clearSwipeAnimation() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
