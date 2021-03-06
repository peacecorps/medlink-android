package gov.peacecorps.medlinkandroid.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.ui.activities.requestslist.RequestsListActivity;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.helpers.Validator;

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    LoginPresenter loginPresenter;

    @Bind(R.id.emailEt)
    EditText emailEt;

    @Bind(R.id.passwordEt)
    EditText passwordEt;

    @Bind(R.id.submitBtn)
    Button submitBtn;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (dataManager.hasUser()) {
            goToRequestsListActivity();
            return;
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void goToRequestsListActivity() {
        Intent intent = new Intent(this, RequestsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @OnTextChanged({R.id.emailEt, R.id.passwordEt})
    public void onTextChanged() {
        if (isEmailValid() && isPasswordValid()) {
            submitBtn.setEnabled(true);
        } else {
            submitBtn.setEnabled(false);
        }
    }

    private boolean isEmailValid() {
        return Validator.isEmailValid(emailEt.getText().toString());
    }

    private boolean isPasswordValid() {
        String password = passwordEt.getText().toString();
        return !TextUtils.isEmpty(password) && password.length() >= Constants.MIN_PASSWORD_LENGTH;
    }

    @OnClick(R.id.submitBtn)
    public void onSubmitBtnClick() {
        loginPresenter.loginUser(emailEt.getText().toString(), passwordEt.getText().toString());
    }
}
