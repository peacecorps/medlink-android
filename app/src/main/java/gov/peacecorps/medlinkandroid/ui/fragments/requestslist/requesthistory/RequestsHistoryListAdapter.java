package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.requesthistory;

import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.SubmittedRequestsListAdapter;

public class RequestsHistoryListAdapter extends SubmittedRequestsListAdapter {
    public RequestsHistoryListAdapter(RequestsListView requestsListView, DataManager dataManager) {
        super(requestsListView, dataManager);
    }

    public void updateSubmittedRequests(List<RequestListItem> requests) {
        refreshRequestsList(sortHistoricalRequestsByStatusAndAddSectionHeaders(requests));
    }

    private List<RequestListItem> sortHistoricalRequestsByStatusAndAddSectionHeaders(List<RequestListItem> requests) {
        List<RequestListItem> deliveredRequests = new LinkedList<>();
        List<RequestListItem> deniedRequests = new LinkedList<>();

        for (RequestListItem request : requests) {
            switch (request.getStatus()) {
                case ALL_APPROVED:
                case PROCESSED:
                    if (request.isNoSupplyPendingUserResponse()) {
                        deliveredRequests.add(DataConverter.convertRequestToRequestListItem(request));
                    }

                    break;
                case ALL_DENIED:
                    deniedRequests.add(DataConverter.convertRequestToRequestListItem(request));
                    break;
            }
        }

        List<RequestListItem> sortedRequests = new LinkedList<>();
        if (requests.isEmpty()) {
            return sortedRequests;
        }

        addSubSection(sortedRequests, deliveredRequests, R.string.sub_section_header_delivered_or_flagged);
        addSubSection(sortedRequests, deniedRequests, R.string.sub_section_header_denied);

        return sortedRequests;
    }
}
