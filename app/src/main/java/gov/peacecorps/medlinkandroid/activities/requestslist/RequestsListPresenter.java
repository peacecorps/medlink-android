package gov.peacecorps.medlinkandroid.activities.requestslist;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.GetRequestsListResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class RequestsListPresenter {
    private final RequestsListView requestsListView;
    private final API api;

    public RequestsListPresenter(RequestsListView requestsListView, API api) {
        this.requestsListView = requestsListView;
        this.api = api;
    }

    public void getRequestsList() {
        requestsListView.getBaseActivity().showProgressDialog(R.string.fetching_requests);
        Call<GetRequestsListResponse> requestsListResponseCall = api.getRequestsList();
        requestsListResponseCall.enqueue(new GlobalRestCallback<GetRequestsListResponse>(requestsListView.getBaseActivity()) {
            @Override
            public void onResponse(Response<GetRequestsListResponse> response, Retrofit retrofit) {
                requestsListView.getBaseActivity().dismissProgressDialog();
                if(response.isSuccess()){
                    requestsListView.displayRequests(response.body().getRequests());
                }
            }
        });
    }
}
