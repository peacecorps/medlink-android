package gov.peacecorps.medlinkandroid.activities.createrequest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.application.AppComponent;

public class CreateRequestActivity extends BaseActivity implements CreateRequestView {

    @Inject
    CreateRequestPresenter createRequestPresenter;

    @Inject
    SupplyListAdapter supplyListAdapter;

    @Bind(R.id.suppliesListRv)
    RecyclerView suppliesListRv;

    @Bind(R.id.specialInstructionsEt)
    EditText specialInstructionsEt;

    @Bind(R.id.submitBtn)
    Button submitBtn;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCreateRequestComponent
                .builder()
                .appComponent(appComponent)
                .createRequestModule(new CreateRequestModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        ButterKnife.bind(this);

        initSuppliesListRv();
    }

    private void initSuppliesListRv() {
        suppliesListRv.setLayoutManager(new LinearLayoutManager(this));
        suppliesListRv.setAdapter(supplyListAdapter);
    }

    @OnClick(R.id.submitBtn)
    public void submitNewRequest() {
        createRequestPresenter.submitNewRequest(supplyListAdapter.getSelectedSupplyIds(), specialInstructionsEt.getText().toString());
    }

    @Override
    public void enableSubmitBtn(boolean enable) {
        submitBtn.setEnabled(enable);
    }

}
