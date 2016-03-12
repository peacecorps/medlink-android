package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class UnsubmittedRequestsFragment extends RequestsListFragment {

    @Inject
    UnsubmittedRequestsPresenter unsubmittedRequestsPresenter;

    @Inject
    UnsubmittedRequestsListAdapter unsubmittedRequestsListAdapter;

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
    public void onResume() {
        super.onResume();
        unsubmittedRequestsPresenter.getUnsubmittedRequests();
    }

    @Override
    protected void initRequestListRecyclerView() {
        requestsListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestsListRv.setAdapter(unsubmittedRequestsListAdapter);
    }

    @Override
    protected void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                unsubmittedRequestsPresenter.getUnsubmittedRequests();
            }
        });
    }

    @Override
    public void updateRequests(List<RequestListItem> requests) {
        super.updateRequests(requests);
        unsubmittedRequestsListAdapter.updateUnsubmittedRequests(requests);
    }
}
