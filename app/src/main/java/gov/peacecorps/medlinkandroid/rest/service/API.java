package gov.peacecorps.medlinkandroid.rest.service;

import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.GetRequestsListResponse;
import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.createrequest.SubmitNewRequestResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface API {
    @POST("auth")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("supplies/all")
    Call<GetSuppliesResponse> getAllSupplies();

    @GET("supplies")
    Call<GetSuppliesResponse> getSupplies();

    @GET("requests")
    Call<GetRequestsListResponse> getRequestsList();

    @POST("requests")
    Call<SubmitNewRequestResponse> submitNewRequest(@Body SubmitNewRequest submitNewRequest);

    @POST("responses/{supply_response_id}/mark_received")
    Call<Void> markSupplyAsReceived(@Path("supply_response_id") Integer supplyResponseId);

    @POST("responses/{supply_response_id}/flag")
    Call<Void> flagSupply(@Path("supply_response_id") Integer supplyResponseId);
}
