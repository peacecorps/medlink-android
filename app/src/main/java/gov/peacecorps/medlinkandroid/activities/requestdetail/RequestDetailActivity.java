package gov.peacecorps.medlinkandroid.activities.requestdetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.activities.requestslist.RequestListItem;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.helpers.DateUtils;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply;

public class RequestDetailActivity extends BaseActivity implements RequestDetailView {

    @Inject
    RequestDetailPresenter requestDetailPresenter;

    @Inject
    SupplyListAdapter supplyListAdapter;

    @Bind(R.id.orderDateTv)
    TextView orderDateTv;

    @Bind(R.id.suppliesListRv)
    RecyclerView suppliesListRv;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRequestDetailComponent
                .builder()
                .appComponent(appComponent)
                .requestDetailModule(new RequestDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        ButterKnife.bind(this);

        RequestListItem requestListItem = (RequestListItem) getIntent().getSerializableExtra(Constants.EXTRA_REQUEST_LIST_ITEM);
        orderDateTv.setText(DateUtils.getDisplayStringFromDate(requestListItem.getCreatedAt()));

        initSupplyListRecyclerView(requestListItem.getSupplies());
    }

    private void initSupplyListRecyclerView(List<Supply> supplies) {
        suppliesListRv.setAdapter(supplyListAdapter);
        suppliesListRv.setLayoutManager(new LinearLayoutManager(this));
        supplyListAdapter.updateSupplies(supplies);
    }
}