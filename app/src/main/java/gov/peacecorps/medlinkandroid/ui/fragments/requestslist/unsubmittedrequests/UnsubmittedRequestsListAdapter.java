package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply;
import gov.peacecorps.medlinkandroid.ui.activities.requestdetail.RequestDetailActivity;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestViewHolder;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class UnsubmittedRequestsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<RequestListItem> requestsList;

    private final DataManager dataManager;
    private UnsubmittedRequestsView unsubmittedRequestsView;

    public UnsubmittedRequestsListAdapter(UnsubmittedRequestsView unsubmittedRequestsView, DataManager dataManager) {
        this.unsubmittedRequestsView = unsubmittedRequestsView;
        this.dataManager = dataManager;
        requestsList = new LinkedList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_list_request, parent, false);

        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RequestListItem requestListItem = requestsList.get(position);
        RequestViewHolder requestViewHolder = (RequestViewHolder) holder;
        requestViewHolder.orderDateTv.setText(unsubmittedRequestsView.getBaseActivity().getString(R.string.not_submitted));
        requestViewHolder.suppliesSnippetTv.setText(getSuppliesNamesSnippet(requestListItem));
        requestViewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRequestDetailActivity(requestListItem);
            }
        });
    }

    private void goToRequestDetailActivity(RequestListItem requestListItem) {
        Intent intent = new Intent(unsubmittedRequestsView.getBaseActivity(), RequestDetailActivity.class);
        intent.putExtra(Constants.EXTRA_REQUEST_LIST_ITEM, requestListItem);

        unsubmittedRequestsView.getBaseActivity().startActivity(intent);
    }

    private String getSuppliesNamesSnippet(RequestListItem requestListItem) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Supply supply : requestListItem.getSupplies()) {
            stringBuilder.append(dataManager.getSupply(supply.getId()).getName());
            stringBuilder.append(", ");
        }

        String result = stringBuilder.toString();
        if (result.isEmpty())
            return result;
        else
            return trimTrailingComma(result);
    }

    @NonNull
    private String trimTrailingComma(String result) {
        return result.substring(0, result.length() - 2);
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void updateUnsubmittedRequests() {
        getUnsubmittedRequests();
        refreshRequestsList();
    }

    private void getUnsubmittedRequests() {
        unsubmittedRequestsView.getBaseActivity().showProgressDialog(R.string.fetching_requests);
        requestsList.clear();

        List<SubmitNewRequest> unsubmittedRequestsFromSharedPrefs = dataManager.getUnsubmittedRequests();

        for (SubmitNewRequest newRequest : unsubmittedRequestsFromSharedPrefs) {
            requestsList.add(DataConverter.convertSubmitNewRequestToRequestListItem(newRequest));
        }
    }

    private void refreshRequestsList() {
        unsubmittedRequestsView.getBaseActivity().dismissProgressDialog();
        unsubmittedRequestsView.clearSwipeAnimation();
        notifyDataSetChanged();
    }
}
