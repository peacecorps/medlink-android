package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class UnsubmittedRequestsPresenter {
    private RequestsListView requestsListView;
    private DataManager dataManager;

    public UnsubmittedRequestsPresenter(RequestsListView requestsListView, DataManager dataManager) {
        this.requestsListView = requestsListView;
        this.dataManager = dataManager;
    }

    public void getUnsubmittedRequests() {
        requestsListView.getBaseActivity().showProgressDialog(R.string.fetching_requests);
        List<RequestListItem> requestsList = new LinkedList<>();

        List<SubmitNewRequest> unsubmittedRequestsFromSharedPrefs = dataManager.getUnsubmittedRequests();

        for (SubmitNewRequest newRequest : unsubmittedRequestsFromSharedPrefs) {
            requestsList.add(DataConverter.convertSubmitNewRequestToRequestListItem(newRequest));
        }

        requestsListView.getBaseActivity().dismissProgressDialog();
        requestsListView.clearSwipeAnimation();
        requestsListView.displayRequests(requestsList);
    }
}
