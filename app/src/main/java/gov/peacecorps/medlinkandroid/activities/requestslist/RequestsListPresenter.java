package gov.peacecorps.medlinkandroid.activities.requestslist;

import java.io.IOException;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.GetRequestsListResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class RequestsListPresenter {
    private final RequestsListView requestsListView;
    private final API api;
    private AppSharedPreferences appSharedPreferences;

    public RequestsListPresenter(RequestsListView requestsListView, API api, AppSharedPreferences appSharedPreferences) {
        this.requestsListView = requestsListView;
        this.api = api;
        this.appSharedPreferences = appSharedPreferences;
    }

    public void getSupplies() {
        requestsListView.getBaseActivity().showProgressDialog(R.string.fetching_requests);

        try {
            appSharedPreferences.getSupplies();
            getRequestsList();
        } catch (IOException e) {
            Call<GetSuppliesResponse> getSuppliesResponseCall = api.getSupplies();
            getSuppliesResponseCall.enqueue(new GlobalRestCallback<GetSuppliesResponse>(requestsListView.getBaseActivity()) {
                @Override
                public void onResponse(Response<GetSuppliesResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        appSharedPreferences.setSupplies(DataConverter.convertGetSuppliesResponseToSupply(response.body()));
                        getRequestsList();
                    } else {
                        requestsListView.getBaseActivity().dismissProgressDialog();
                        requestsListView.getBaseActivity().showInfoDialog(R.string.we_are_having_technical_issues);
                    }
                }
            });
        }
    }

    public void getRequestsList() {
        Call<GetRequestsListResponse> getRequestsListResponseCall = api.getRequestsList();
        getRequestsListResponseCall.enqueue(new GlobalRestCallback<GetRequestsListResponse>(requestsListView.getBaseActivity()) {
            @Override
            public void onResponse(Response<GetRequestsListResponse> response, Retrofit retrofit) {
                requestsListView.getBaseActivity().dismissProgressDialog();
                if (response.isSuccess()) {
                    requestsListView.displayRequests(response.body().getRequests());
                } else {
                    requestsListView.clearSwipeAnimation();
                    requestsListView.getBaseActivity().showInfoDialog(R.string.we_are_having_technical_issues);
                }
            }
        });
    }
}
