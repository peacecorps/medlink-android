package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestViewHolder;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListAdapter;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class UnsubmittedRequestsListAdapter extends RequestsListAdapter {

    private RequestsListView requestsListView;

    public UnsubmittedRequestsListAdapter(RequestsListView requestsListView, DataManager dataManager) {
        super(requestsListView, dataManager);
        this.requestsListView = requestsListView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RequestListItem requestListItem = requestsList.get(position);
        RequestViewHolder requestViewHolder = (RequestViewHolder) holder;
        requestViewHolder.orderDateTv.setText(requestsListView.getBaseActivity().getString(R.string.not_submitted));
        requestViewHolder.suppliesSnippetTv.setText(getSuppliesNamesSnippet(requestListItem));
        requestViewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRequestDetailActivity(requestListItem);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void updateUnsubmittedRequests(List<RequestListItem> requests) {
        refreshRequestsList(requests);
    }

}
