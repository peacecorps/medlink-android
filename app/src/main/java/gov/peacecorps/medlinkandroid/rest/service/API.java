package gov.peacecorps.medlinkandroid.rest.service;

import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.GetRequestsListResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.BaseResponse;
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

    @GET("supplies")
    Call<GetSuppliesResponse> getSupplies();

    @GET("requests")
    Call<GetRequestsListResponse> getRequestsList();

    @POST("requests")
    Call<BaseResponse> submitNewRequest(@Body SubmitNewRequest submitNewRequest);

    @POST("responses/{supply_id}/mark_received")
    Call<BaseResponse> markSupplyAsReceived(@Path("supply_id") Integer supplyId);
}
