package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests;

import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.createrequest.SubmitNewRequestResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class UnsubmittedRequestsPresenter {
    private RequestsListView requestsListView;
    private API api;
    private DataManager dataManager;

    public UnsubmittedRequestsPresenter(RequestsListView requestsListView, API api, DataManager dataManager) {
        this.requestsListView = requestsListView;
        this.api = api;
        this.dataManager = dataManager;
    }

    public void getUnsubmittedRequests() {
        requestsListView.getBaseActivity().showProgressDialog(R.string.fetching_requests);
        updateRequestsList(dataManager.getUnsubmittedRequests());
        requestsListView.clearSwipeAnimation();
    }

    private void updateRequestsList(List<SubmitNewRequest> unsubmittedRequestsFromSharedPrefs) {
        List<RequestListItem> requestsList = new LinkedList<>();
        for (SubmitNewRequest newRequest : unsubmittedRequestsFromSharedPrefs) {
            requestsList.add(DataConverter.convertSubmitNewRequestToRequestListItem(newRequest));
        }

        requestsListView.updateRequests(requestsList);
    }

    public void sendUnsubmittedRequests() {
        List<SubmitNewRequest> unsubmittedRequests = dataManager.getUnsubmittedRequests();
        BaseActivity baseActivity = requestsListView.getBaseActivity();
        if (unsubmittedRequests.isEmpty()) {
            updateRequestsList(unsubmittedRequests);
            baseActivity.dismissProgressDialog();
            return;
        }

        baseActivity.showProgressDialog(R.string.submitting_orders);
        sendFirstUnsubmittedRequest(unsubmittedRequests);
    }

    private void sendFirstUnsubmittedRequest(final List<SubmitNewRequest> unsubmittedRequests) {
        final BaseActivity baseActivity = requestsListView.getBaseActivity();

        Call<SubmitNewRequestResponse> submitNewRequestCall = api.submitNewRequest(unsubmittedRequests.remove(0));
        submitNewRequestCall.enqueue(new GlobalRestCallback<SubmitNewRequestResponse>(baseActivity) {
            @Override
            public void onResponse(Response<SubmitNewRequestResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    dataManager.setUnsubmittedRequests(unsubmittedRequests);
                    updateRequestsList(unsubmittedRequests);
                    sendUnsubmittedRequests();
                } else {
                    baseActivity.dismissProgressDialog();
                    baseActivity.showInfoDialog(R.string.we_are_having_technical_issues);
                }
            }
        });
    }
}
