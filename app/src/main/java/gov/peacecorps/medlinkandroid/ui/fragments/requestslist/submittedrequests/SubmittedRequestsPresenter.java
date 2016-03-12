package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import java.util.List;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.data.models.Supply;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.GetRequestsListResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class SubmittedRequestsPresenter {
    private final SubmittedRequestsView submittedRequestsView;
    private final API api;
    private DataManager dataManager;

    public SubmittedRequestsPresenter(SubmittedRequestsView submittedRequestsView, API api, DataManager dataManager) {
        this.submittedRequestsView = submittedRequestsView;
        this.api = api;
        this.dataManager = dataManager;
    }

    public void getSupplies() {
        submittedRequestsView.getBaseActivity().showProgressDialog(R.string.fetching_requests);

        List<Supply> supplies = dataManager.getSupplies();
        if (supplies.isEmpty()) {
            Call<GetSuppliesResponse> getSuppliesResponseCall = api.getSupplies();
            getSuppliesResponseCall.enqueue(new GlobalRestCallback<GetSuppliesResponse>(submittedRequestsView.getBaseActivity()) {
                @Override
                public void onResponse(Response<GetSuppliesResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        dataManager.setSupplies(DataConverter.convertGetSuppliesResponseToSupply(response.body()));
                        getRequestsList();
                    } else {
                        submittedRequestsView.getBaseActivity().dismissProgressDialog();
                        submittedRequestsView.getBaseActivity().showInfoDialog(R.string.we_are_having_technical_issues);
                    }
                }
            });
        } else {
            getRequestsList();
        }
    }

    public void getRequestsList() {
        Call<GetRequestsListResponse> getRequestsListResponseCall = api.getRequestsList();
        final GlobalRestCallback.NetworkFailureCallback networkFailureCallback = new GlobalRestCallback.NetworkFailureCallback() {
            @Override
            public void onNetworkFailure() {
                submittedRequestsView.clearSwipeAnimation();
                super.onNetworkFailure();
            }
        };

        getRequestsListResponseCall.enqueue(new GlobalRestCallback<GetRequestsListResponse>(submittedRequestsView.getBaseActivity(), networkFailureCallback) {
            @Override
            public void onResponse(Response<GetRequestsListResponse> response, Retrofit retrofit) {
                submittedRequestsView.getBaseActivity().dismissProgressDialog();
                submittedRequestsView.clearSwipeAnimation();
                if (response.isSuccess()) {
                    submittedRequestsView.displaySubmittedRequests(response.body().getRequests());
                } else {
                    submittedRequestsView.getBaseActivity().showInfoDialog(R.string.we_are_having_technical_issues);
                }
            }
        });
    }
}
