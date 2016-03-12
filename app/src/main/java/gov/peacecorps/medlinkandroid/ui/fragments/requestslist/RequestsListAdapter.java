package gov.peacecorps.medlinkandroid.ui.fragments.requestslist;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.ui.activities.BaseView;
import gov.peacecorps.medlinkandroid.ui.activities.requestdetail.RequestDetailActivity;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public abstract class RequestsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final DataManager dataManager;
    protected final BaseActivity context;
    protected final List<RequestListItem> requestsList;

    public RequestsListAdapter(BaseView baseView, DataManager dataManager) {
        context = baseView.getBaseActivity();
        this.dataManager = dataManager;
        requestsList = new LinkedList<>();
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    protected void goToRequestDetailActivity(RequestListItem requestListItem) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        intent.putExtra(Constants.EXTRA_REQUEST_LIST_ITEM, requestListItem);

        context.startActivity(intent);
    }

    protected String getSuppliesNamesSnippet(RequestListItem requestListItem) {
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

    protected String trimTrailingComma(String result) {
        return result.substring(0, result.length() - 2);
    }

    protected void refreshRequestsList(List<RequestListItem> requests) {
        requestsList.clear();
        requestsList.addAll(requests);

        notifyDataSetChanged();
    }
}
