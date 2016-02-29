package gov.peacecorps.medlinkandroid.rest.service;

import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.GetRequestsListResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface API {
    @POST("auth")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("supplies")
    Call<GetSuppliesResponse> getSupplies();

    @GET("requests")
    Call<GetRequestsListResponse> getRequestsList();
}
