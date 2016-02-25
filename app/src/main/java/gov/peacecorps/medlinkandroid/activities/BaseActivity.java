package gov.peacecorps.medlinkandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.application.App;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void setupActivityComponent(AppComponent appComponent);

    private MaterialDialog dialog;

    @Inject
    protected AppSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bootstrapDI();
        initProgressDialog();
    }

    private void initProgressDialog() {
        if(dialog == null){
            dialog = new MaterialDialog.Builder(this)
                    .cancelable(false)
                    .build();
        }
    }

    public void showProgressDialog(){
        if(dialog != null && !dialog.isShowing()) dialog.show();
    }

    public void dismissProgressDialog(){
        if(dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dialog = null;
        super.onDestroy();
    }

    protected void bootstrapDI(){
        AppComponent appComponent = ((App) getApplication()).getAppComponent();
        DaggerBaseComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        setupActivityComponent(appComponent);
    }

    public void showMaterialDialog(int resId){
        showMaterialDialog(resId, new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
            }
        });
    }

    private void showMaterialDialog(int resId, MaterialDialog.ButtonCallback callback){
        new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(resId)
                .positiveText(R.string.okay)
                .callback(callback)
                .build()
                .show();
    }
}
