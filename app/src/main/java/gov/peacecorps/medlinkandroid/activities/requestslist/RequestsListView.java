package gov.peacecorps.medlinkandroid.activities.requestslist;

import java.util.List;

import gov.peacecorps.medlinkandroid.activities.BaseView;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;

public interface RequestsListView extends BaseView {
    void displayRequests(List<Request> requests);
}
