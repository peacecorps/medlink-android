package gov.peacecorps.medlinkandroid.rest;

import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface API {
    @POST("auth")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
