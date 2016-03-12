package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import java.util.List;

import gov.peacecorps.medlinkandroid.ui.activities.BaseView;

public interface SubmittedRequestsView extends BaseView {
    void displaySubmittedRequests(List<RequestListItem> requests);
    void clearSwipeAnimation();
}
