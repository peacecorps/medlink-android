package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListFragment;

public class SubmittedRequestsFragment extends RequestsListFragment {

    @Inject
    SubmittedRequestsPresenter submittedRequestsPresenter;

    @Inject
    SubmittedRequestsListAdapter submittedRequestsListAdapter;

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
        submittedRequestsPresenter.getSupplies();
    }

    @Override
    protected void initRequestListRecyclerView() {
        requestsListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestsListRv.setAdapter(submittedRequestsListAdapter);
    }

    @Override
    protected void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                submittedRequestsPresenter.getSupplies();
            }
        });
    }

    @Override
    public void updateRequests(List<RequestListItem> requests) {
        super.updateRequests(requests);
        submittedRequestsListAdapter.updateSubmittedRequests(requests);
    }
}
