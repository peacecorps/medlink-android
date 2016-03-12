package gov.peacecorps.medlinkandroid.activities.requestslist;

import java.util.List;

import gov.peacecorps.medlinkandroid.activities.BaseView;

public interface RequestsListView extends BaseView {
    void displaySubmittedRequests(List<RequestListItem> requests);
    void clearSwipeAnimation();
    void displayUnsubmittedRequests();
}
