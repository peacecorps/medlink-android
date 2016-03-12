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
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListView;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class SubmittedRequestsPresenter {
    private final RequestsListView requestsListView;
    private final API api;
    private DataManager dataManager;

    public SubmittedRequestsPresenter(RequestsListView requestsListView, API api, DataManager dataManager) {
        this.requestsListView = requestsListView;
        this.api = api;
        this.dataManager = dataManager;
    }

    public void getSupplies() {
        requestsListView.getBaseActivity().showProgressDialog(R.string.fetching_requests);

        List<Supply> supplies = dataManager.getSupplies();
        if (supplies.isEmpty()) {
            Call<GetSuppliesResponse> getSuppliesResponseCall = api.getSupplies();
            getSuppliesResponseCall.enqueue(new GlobalRestCallback<GetSuppliesResponse>(requestsListView.getBaseActivity()) {
                @Override
                public void onResponse(Response<GetSuppliesResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        dataManager.setSupplies(DataConverter.convertGetSuppliesResponseToSupply(response.body()));
                        getSubmittedRequestsList();
                    } else {
                        requestsListView.getBaseActivity().dismissProgressDialog();
                        requestsListView.getBaseActivity().showInfoDialog(R.string.we_are_having_technical_issues);
                    }
                }
            });
        } else {
            getSubmittedRequestsList();
        }
    }

    public void getSubmittedRequestsList() {
        Call<GetRequestsListResponse> getRequestsListResponseCall = api.getRequestsList();
        final GlobalRestCallback.NetworkFailureCallback networkFailureCallback = new GlobalRestCallback.NetworkFailureCallback() {
            @Override
            public void onNetworkFailure() {
                requestsListView.clearSwipeAnimation();
                super.onNetworkFailure();
            }
        };

        getRequestsListResponseCall.enqueue(new GlobalRestCallback<GetRequestsListResponse>(requestsListView.getBaseActivity(), networkFailureCallback) {
            @Override
            public void onResponse(Response<GetRequestsListResponse> response, Retrofit retrofit) {
                requestsListView.getBaseActivity().dismissProgressDialog();
                requestsListView.clearSwipeAnimation();
                if (response.isSuccess()) {
                    requestsListView.displayRequests(response.body().getRequests());
                } else {
                    requestsListView.getBaseActivity().showInfoDialog(R.string.we_are_having_technical_issues);
                }
            }
        });
    }
}
