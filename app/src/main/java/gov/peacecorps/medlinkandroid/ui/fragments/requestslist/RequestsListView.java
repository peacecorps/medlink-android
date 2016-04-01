package gov.peacecorps.medlinkandroid.ui.fragments.requestslist;

import java.util.List;

import gov.peacecorps.medlinkandroid.ui.activities.BaseView;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public interface RequestsListView extends BaseView {
    void updateRequests(List<RequestListItem> requests);
    void clearSwipeAnimation();
}
