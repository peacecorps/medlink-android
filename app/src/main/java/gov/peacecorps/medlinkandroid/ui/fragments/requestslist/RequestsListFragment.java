package gov.peacecorps.medlinkandroid.ui.fragments.requestslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.fragments.BaseFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public abstract class RequestsListFragment extends BaseFragment implements RequestsListView {

    protected abstract void initRequestListRecyclerView();

    protected abstract void initSwipeRefreshLayout();

    @Bind(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.requestsListRv)
    protected RecyclerView requestsListRv;

    @Bind(R.id.emptyNoOrdersPlaceHolderTv)
    TextView emptyNoOrdersPlaceHolderTv;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
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

        initSwipeRefreshLayout();

        return view;
    }

    @Override
    public void updateRequests(List<RequestListItem> requests) {
        if (requests.isEmpty()) {
            emptyNoOrdersPlaceHolderTv.setVisibility(View.VISIBLE);
        } else {
            emptyNoOrdersPlaceHolderTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearSwipeAnimation() {
        getBaseActivity().dismissProgressDialog();
        swipeRefreshLayout.setRefreshing(false);
    }
}
