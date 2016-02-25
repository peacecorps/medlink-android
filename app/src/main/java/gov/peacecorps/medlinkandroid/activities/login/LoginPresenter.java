package gov.peacecorps.medlinkandroid.activities.login;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.data.models.User;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.rest.API;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.login.LoginRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
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
        Call<LoginResponse> loginResponseCall = api.loginUser(buildLoginRequestPayload(email, password));
        loginResponseCall.enqueue(new GlobalRestCallback<LoginResponse>(loginView.getBaseActivity()) {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                loginView.getBaseActivity().dismissProgressDialog();
                if(response.isSuccess()){
                    appSharedPreferences.setUser(buildUser(response.body()));
                    loginView.goToHomeActivity();
                } else {
                    loginView.getBaseActivity().showMaterialDialog(R.string.invalid_login);
                }
            }
        });
    }

    private User buildUser(LoginResponse loginResponse) {
        User user = new User();
        user.setSecretKey(loginResponse.getSecretKey());
        user.setUserId(loginResponse.getId());

        return user;
    }

    private LoginRequest buildLoginRequestPayload(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        return loginRequest;
    }
}
