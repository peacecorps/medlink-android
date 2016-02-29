package gov.peacecorps.medlinkandroid.activities.login;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginPresenter {
    private final AppSharedPreferences appSharedPreferences;
    private final LoginView loginView;
    private final API api;

    public LoginPresenter(LoginView loginView, API api, AppSharedPreferences appSharedPreferences) {
        this.loginView = loginView;
        this.api = api;
        this.appSharedPreferences = appSharedPreferences;
    }

    public void loginUser(String email, String password) {
        loginView.getBaseActivity().showProgressDialog(R.string.logging_in);
        Call<LoginResponse> loginResponseCall = api.loginUser(buildLoginRequestPayload(email, password));
        loginResponseCall.enqueue(new GlobalRestCallback<LoginResponse>(loginView.getBaseActivity()) {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    appSharedPreferences.setUser(DataConverter.convertLoginResponseToUser(response.body()));
                    fetchSupplies();
                } else {
                    loginView.getBaseActivity().dismissProgressDialog();
                    loginView.getBaseActivity().showMaterialDialog(R.string.invalid_login);
                }
            }
        });
    }

    private void fetchSupplies() {
        Call<GetSuppliesResponse> getSuppliesResponseCall = api.getSupplies();
        getSuppliesResponseCall.enqueue(new GlobalRestCallback<GetSuppliesResponse>(loginView.getBaseActivity()) {
            @Override
            public void onResponse(Response<GetSuppliesResponse> response, Retrofit retrofit) {
                loginView.getBaseActivity().dismissProgressDialog();
                if (response.isSuccess()) {
                    appSharedPreferences.setSupplies(DataConverter.convertGetSuppliesResponseToSupply(response.body()));
                    loginView.goToRequestsListActivity();
                } else {
                    loginView.getBaseActivity().showMaterialDialog(R.string.could_not_fetch_supplies);
                }
            }
        });
    }

    private LoginRequest buildLoginRequestPayload(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        return loginRequest;
    }
}
