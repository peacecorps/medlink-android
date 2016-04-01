package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.requesthistory;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.DaggerSubmittedRequestsComponent;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.SubmittedRequestsFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.SubmittedRequestsModule;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.SubmittedRequestsPresenter;

public class RequestsHistoryFragment extends SubmittedRequestsFragment {

    @Inject
    SubmittedRequestsPresenter submittedRequestsPresenter;

    @Inject
    RequestsHistoryListAdapter requestsHistoryListAdapter;

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
    public void onResume() {
        super.onResume();
        submittedRequestsPresenter.getSubmittedRequestsList();
    }

    @Override
    protected void initRequestListRecyclerView() {
        requestsListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestsListRv.setAdapter(requestsHistoryListAdapter);
    }

    @Override
    protected void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                submittedRequestsPresenter.getSubmittedRequestsList();
            }
        });
    }

    @Override
    public void updateRequests(List<RequestListItem> requests) {
        super.updateRequests(requests);
        requestsHistoryListAdapter.updateSubmittedRequests(requests);
    }
}
