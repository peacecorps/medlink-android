package gov.peacecorps.medlinkandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.login.LoginActivity;
import gov.peacecorps.medlinkandroid.application.App;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected abstract void setupActivityComponent(AppComponent appComponent);

    private MaterialDialog dialog;

    @Inject
    protected AppSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bootstrapDI();
    }

    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void bootstrapDI() {
        AppComponent appComponent = ((App) getApplication()).getAppComponent();
        DaggerBaseComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        setupActivityComponent(appComponent);
    }

    public void showProgressDialog(int resId) {
        dialog = new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(resId)
                .progress(true, 0)
                .build();
        dialog.show();
    }

    public void showMaterialDialog(int resId) {
        showMaterialDialog(resId, new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
            }
        });
    }

    private void showMaterialDialog(int resId, MaterialDialog.ButtonCallback callback) {
        dialog = new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(resId)
                .positiveText(R.string.okay)
                .callback(callback)
                .build();

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            sharedPreferences.deleteUser();
            goToLoginScreen();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToLoginScreen() {
        showProgressDialog(R.string.logging_out);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        dismissProgressDialog();

        finish();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }
}
