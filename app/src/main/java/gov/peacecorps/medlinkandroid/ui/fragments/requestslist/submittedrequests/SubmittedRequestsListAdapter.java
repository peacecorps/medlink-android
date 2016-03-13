package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestViewHolder;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListAdapter;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;

public class SubmittedRequestsListAdapter extends RequestsListAdapter {

    private final static int VIEW_TYPE_SUB_SECTION_HEADER = 1;

    public SubmittedRequestsListAdapter(RequestsListView requestsListView, DataManager dataManager) {
        super(requestsListView, dataManager);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SUB_SECTION_HEADER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_list_sub_section_header, parent, false);
                return new SubSectionHeaderViewHolder(view);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RequestListItem requestListItem = requestsList.get(position);
        if (requestListItem.isSubSectionHeader()) {
            SubSectionHeaderViewHolder subSectionHeaderViewHolder = (SubSectionHeaderViewHolder) holder;
            subSectionHeaderViewHolder.subSectionNameTv.setText(requestListItem.getSubSectionHeaderName());
        } else {
            RequestViewHolder requestViewHolder = (RequestViewHolder) holder;
            requestViewHolder.orderDateTv.setText(requestListItem.getDateString(context));
            requestViewHolder.suppliesSnippetTv.setText(getSuppliesNamesSnippet(requestListItem));
            requestViewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToRequestDetailActivity(requestListItem);
                }
            });
        }
    }

    public void updateSubmittedRequests(List<RequestListItem> requests) {
        refreshRequestsList(sortSubmittedRequestsByStatusAndAddSectionHeaders(requests));
    }

    @Override
    public int getItemViewType(int position) {
        RequestListItem requestListItem = requestsList.get(position);

        if (requestListItem.isSubSectionHeader()) {
            return VIEW_TYPE_SUB_SECTION_HEADER;
        }

        return super.getItemViewType(position);
    }

    private RequestListItem createSubSectionHeader(String subSectionName) {
        RequestListItem requestListItem = new RequestListItem();
        requestListItem.setIsSubSectionHeader(true);
        requestListItem.setSubSectionHeaderName(subSectionName);

        return requestListItem;
    }

    private List<RequestListItem> sortSubmittedRequestsByStatusAndAddSectionHeaders(List<RequestListItem> requests) {
        List<RequestListItem> approvedRequests = new LinkedList<>();
        List<RequestListItem> deniedRequests = new LinkedList<>();
        List<RequestListItem> pendingRequests = new LinkedList<>();
        List<RequestListItem> processedRequests = new LinkedList<>();

        for (RequestListItem request : requests) {
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
        if (requests.isEmpty()) {
            return sortedRequests;
        }

        addSubSection(sortedRequests, approvedRequests, R.string.sub_section_header_approved);
        addSubSection(sortedRequests, processedRequests, R.string.sub_section_header_processed);
        addSubSection(sortedRequests, pendingRequests, R.string.sub_section_header_pending);
        addSubSection(sortedRequests, deniedRequests, R.string.sub_section_header_denied);

        return sortedRequests;
    }

    private void addSubSection(List<RequestListItem> sortedRequests, List<RequestListItem> subSectionRequests, int subSectionHeaderResId) {
        if (!subSectionRequests.isEmpty()) {
            sortedRequests.add(createSubSectionHeader(context.getString(subSectionHeaderResId)));
            sortedRequests.addAll(subSectionRequests);
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
