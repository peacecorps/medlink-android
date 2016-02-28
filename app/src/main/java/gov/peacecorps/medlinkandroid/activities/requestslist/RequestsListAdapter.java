package gov.peacecorps.medlinkandroid.activities.requestslist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.requestdetail.RequestDetailActivity;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DateUtils;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply;

public class RequestsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<RequestListItem> requestsList;

    private final static int VIEW_TYPE_SECTION_HEADER = 0;
    private final static int VIEW_TYPE_REQUEST = 1;
    private final AppSharedPreferences appSharedPreferences;

    public RequestsListAdapter(RequestsListView requestsListView, AppSharedPreferences appSharedPreferences) {
        this.context = requestsListView.getBaseActivity();
        this.appSharedPreferences = appSharedPreferences;
        this.requestsList = new LinkedList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SECTION_HEADER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_list_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_list_request, parent, false);
                return new RequestViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RequestListItem requestListItem = requestsList.get(position);
        if (requestListItem.isSectionHeader()) {
            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
            sectionHeaderViewHolder.sectionNameTv.setText(requestListItem.getSectionHeaderName());
        } else {
            RequestViewHolder requestViewHolder = (RequestViewHolder) holder;
            requestViewHolder.orderDateTv.setText(DateUtils.getDisplayStringFromDate(requestListItem.getCreatedAt()));
            requestViewHolder.suppliesSnippetTv.setText(getSuppliesNamesSnippet(requestListItem));
            requestViewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToRequestDetailActivity(requestListItem);
                }
            });
        }
    }

    private void goToRequestDetailActivity(RequestListItem requestListItem) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        intent.putExtra(Constants.EXTRA_REQUEST_LIST_ITEM, requestListItem);

        context.startActivity(intent);
    }

    private String getSuppliesNamesSnippet(RequestListItem requestListItem) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Supply supply : requestListItem.getSupplies()) {
            stringBuilder.append(appSharedPreferences.getSupply(supply.getId()).getName());
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
        RequestListItem requestListItem = requestsList.get(position);
        if (requestListItem.isSectionHeader()) {
            return VIEW_TYPE_SECTION_HEADER;
        }

        return VIEW_TYPE_REQUEST;
    }

    private RequestListItem createSectionHeader(String sectionName) {
        RequestListItem requestListItem = new RequestListItem();
        requestListItem.setIsSectionHeader(true);
        requestListItem.setSectionHeaderName(sectionName);

        return requestListItem;
    }

    public void updateRequests(List<Request> requests) {
        this.requestsList.clear();
        this.requestsList.addAll(sortRequestsByStatusAndAddSectionHeaders(requests));
        notifyDataSetChanged();
    }

    private List<RequestListItem> sortRequestsByStatusAndAddSectionHeaders(List<Request> requests) {
        List<RequestListItem> approvedRequests = new LinkedList<>();
        List<RequestListItem> deniedRequests = new LinkedList<>();
        List<RequestListItem> pendingRequests = new LinkedList<>();
        List<RequestListItem> processedRequests = new LinkedList<>();

        for (Request request : requests) {
            switch (request.getStatus()) {
                case ALL_APPROVED:
                    approvedRequests.add(DataConverter.convertRequestToRequestListItem(request));
                    break;
                case ALL_DENIED:
                    deniedRequests.add(DataConverter.convertRequestToRequestListItem(request));
                    break;
                case AT_LEAST_ONE_PENDING:
                    pendingRequests.add(DataConverter.convertRequestToRequestListItem(request));
                    break;
                case PROCESSED:
                    processedRequests.add(DataConverter.convertRequestToRequestListItem(request));
                    break;
            }
        }

        List<RequestListItem> sortedRequests = new LinkedList<>();

        addSection(sortedRequests, approvedRequests, R.string.section_header_approved);
        addSection(sortedRequests, processedRequests, R.string.section_header_processed);
        addSection(sortedRequests, pendingRequests, R.string.section_header_pending);
        addSection(sortedRequests, deniedRequests, R.string.section_header_denied);

        return sortedRequests;
    }

    private void addSection(List<RequestListItem> sortedRequests, List<RequestListItem> sectionRequests, int sectionHeaderResId) {
        if (!sectionRequests.isEmpty()) {
            sortedRequests.add(createSectionHeader(context.getString(sectionHeaderResId)));
            sortedRequests.addAll(sectionRequests);
        }
    }

    public static final class RequestViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rowLayout)
        RelativeLayout rowLayout;

        @Bind(R.id.orderDateTv)
        TextView orderDateTv;

        @Bind(R.id.suppliesSnippetTv)
        TextView suppliesSnippetTv;

        public RequestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static final class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sectionNameTv)
        TextView sectionNameTv;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
