package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.helpers.NetworkManager;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class UnsubmittedRequestsFragment extends RequestsListFragment {

    @Inject
    UnsubmittedRequestsPresenter unsubmittedRequestsPresenter;

    @Inject
    UnsubmittedRequestsListAdapter unsubmittedRequestsListAdapter;

    private boolean networkConnected;

    private ConnectivityChangeBroadcastReceiver connectivityChangeBroadcastReceiver;

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
        registerConnectivityChangeBroadcastReceiver();
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
    public void onPrepareOptionsMenu(Menu menu) {
        if (networkConnected) {
            menu.findItem(R.id.action_send_unsubmitted_requests).setVisible(true);
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == R.id.action_send_unsubmitted_requests) {
            unsubmittedRequestsPresenter.sendUnsubmittedRequests();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterConnectivityChangeBroadcastReceiver();
    }

    @Override
    public void updateRequests(List<RequestListItem> requests) {
        super.updateRequests(requests);
        unsubmittedRequestsListAdapter.updateUnsubmittedRequests(requests);
    }

    private void registerConnectivityChangeBroadcastReceiver() {
        connectivityChangeBroadcastReceiver = new ConnectivityChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getBaseActivity().registerReceiver(connectivityChangeBroadcastReceiver, intentFilter);
    }

    private void unregisterConnectivityChangeBroadcastReceiver() {
        if (connectivityChangeBroadcastReceiver != null) {
            getBaseActivity().unregisterReceiver(connectivityChangeBroadcastReceiver);
            connectivityChangeBroadcastReceiver = null;
        }
    }

    private class ConnectivityChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            networkConnected = NetworkManager.isNetworkConnected(context);
            getBaseActivity().invalidateOptionsMenu();
        }
    }
}
