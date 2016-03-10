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
import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.helpers.DateUtils;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply;

public class RequestsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<RequestListItem> requestsList;

    private final static int VIEW_TYPE_SECTION_HEADER = 0;
    private final static int VIEW_TYPE_SUB_SECTION_HEADER = 1;
    private final static int VIEW_TYPE_REQUEST = 2;

    private final DataManager dataManager;
    private List<RequestListItem> sortedRequests;
    private List<RequestListItem> unsubmittedRequests;

    public RequestsListAdapter(RequestsListView requestsListView, DataManager dataManager) {
        this.context = requestsListView.getBaseActivity();
        this.dataManager = dataManager;
        requestsList = new LinkedList<>();
        sortedRequests = new LinkedList<>();
        unsubmittedRequests = new LinkedList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SECTION_HEADER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_list_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            case VIEW_TYPE_SUB_SECTION_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_list_sub_section_header, parent, false);
                return new SubSectionHeaderViewHolder(view);
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
        } else if (requestListItem.isSubSectionHeader()) {
            SubSectionHeaderViewHolder subSectionHeaderViewHolder = (SubSectionHeaderViewHolder) holder;
            subSectionHeaderViewHolder.subSectionNameTv.setText(requestListItem.getSubSectionHeaderName());
        } else {
            RequestViewHolder requestViewHolder = (RequestViewHolder) holder;
            requestViewHolder.orderDateTv.setText(DateUtils.getDisplayStringFromDate(requestListItem.getCreatedAt(), context));
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
        RequestListItem requestListItem = requestsList.get(position);

        if (requestListItem.isSectionHeader()){
            return VIEW_TYPE_SECTION_HEADER;
        }

        if (requestListItem.isSubSectionHeader()) {
            return VIEW_TYPE_SUB_SECTION_HEADER;
        }

        return VIEW_TYPE_REQUEST;
    }

    private RequestListItem createSectionHeader(String sectionName) {
        RequestListItem requestListItem = new RequestListItem();
        requestListItem.setIsSectionHeader(true);
        requestListItem.setSectionHeaderName(sectionName);

        return requestListItem;
    }

    private RequestListItem createSubSectionHeader(String subSectionName) {
        RequestListItem requestListItem = new RequestListItem();
        requestListItem.setIsSubSectionHeader(true);
        requestListItem.setSubSectionHeaderName(subSectionName);

        return requestListItem;
    }

    public void updateUnsubmittedRequests() {
        getUnsubmittedRequests();
        refreshRequestsList();
    }

    public void updateSubmittedRequests(List<RequestListItem> requests) {
        sortSubmittedRequestsByStatusAndAddSectionHeaders(requests);
        refreshRequestsList();
    }

    private void refreshRequestsList() {
        this.requestsList.clear();
        this.requestsList.addAll(unsubmittedRequests);
        this.requestsList.addAll(sortedRequests);

        notifyDataSetChanged();
    }

    private void getUnsubmittedRequests() {
        unsubmittedRequests.clear();
        addSection(unsubmittedRequests, R.string.section_header_unsubmitted_orders);

        for(SubmitNewRequest newRequest: dataManager.getUnsubmittedRequests()){
            unsubmittedRequests.add(DataConverter.convertSubmitNewRequestToRequestListItem(newRequest));
        }
    }

    private List<RequestListItem> sortSubmittedRequestsByStatusAndAddSectionHeaders(List<RequestListItem> requests) {
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

        sortedRequests.clear();

        addSection(sortedRequests, R.string.section_header_submitted_orders);

        addSubSection(sortedRequests, approvedRequests, R.string.sub_section_header_approved);
        addSubSection(sortedRequests, processedRequests, R.string.sub_section_header_processed);
        addSubSection(sortedRequests, pendingRequests, R.string.sub_section_header_pending);
        addSubSection(sortedRequests, deniedRequests, R.string.sub_section_header_denied);

        return sortedRequests;
    }

    private void addSection(List<RequestListItem> sortedRequests, int sectionHeaderResId){
        sortedRequests.add(createSectionHeader(context.getString(sectionHeaderResId)));
    }

    private void addSubSection(List<RequestListItem> sortedRequests, List<RequestListItem> subSectionRequests, int subSectionHeaderResId) {
        if (!subSectionRequests.isEmpty()) {
            sortedRequests.add(createSubSectionHeader(context.getString(subSectionHeaderResId)));
            sortedRequests.addAll(subSectionRequests);
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

    public static final class SubSectionHeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.subSectionNameTv)
        TextView subSectionNameTv;

        public SubSectionHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
