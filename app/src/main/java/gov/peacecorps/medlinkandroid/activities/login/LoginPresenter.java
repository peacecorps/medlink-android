package gov.peacecorps.medlinkandroid.activities.login;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataConverter;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginPresenter {
    private final DataManager dataManager;
    private final LoginView loginView;
    private final API api;

    public LoginPresenter(LoginView loginView, API api, DataManager dataManager) {
        this.loginView = loginView;
        this.api = api;
        this.dataManager = dataManager;
    }

    public void loginUser(String email, String password) {
        loginView.getBaseActivity().showProgressDialog(R.string.logging_in);
        Call<LoginResponse> loginResponseCall = api.loginUser(buildLoginRequestPayload(email, password));
        loginResponseCall.enqueue(new GlobalRestCallback<LoginResponse>(loginView.getBaseActivity()) {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    dataManager.setUser(DataConverter.convertLoginResponseToUser(response.body()));
                    loginView.goToRequestsListActivity();
                } else {
                    loginView.getBaseActivity().dismissProgressDialog();
                    loginView.getBaseActivity().showInfoDialog(R.string.invalid_login);
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
