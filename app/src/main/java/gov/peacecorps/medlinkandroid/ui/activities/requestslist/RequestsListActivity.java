package gov.peacecorps.medlinkandroid.ui.activities.requestslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.adapters.requestslist.OrderPagerAdapter;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.ui.activities.createrequest.CreateRequestActivity;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.SubmittedRequestsFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests.UnsubmittedRequestsFragment;

public class RequestsListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.newOrderFab)
    FloatingActionButton newOrderFab;

    @Bind(R.id.orderHistoryFab)
    FloatingActionButton orderHistoryFab;

    @Bind(R.id.orderTabs)
    TabLayout ordersTabLayout;

    @Bind(R.id.ordersViewPager)
    ViewPager ordersViewPager;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        orderHistoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open activity that shows order history
                Snackbar.make(view, R.string.not_yet_implemented, Snackbar.LENGTH_SHORT).show();
            }
        });

        newOrderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateRequestsActivity();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupOrdersTabLayout();
    }

    private void setupOrdersTabLayout() {
        ordersTabLayout.setupWithViewPager(buildOrdersViewPager());
    }

    private ViewPager buildOrdersViewPager() {
        ordersViewPager.setAdapter(buildOrderPagerAdapter());

        return ordersViewPager;
    }

    private OrderPagerAdapter buildOrderPagerAdapter() {
        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager());
        addSubmittedRequestsFragment(orderPagerAdapter);
        addUnsubmittedRequestsFragment(orderPagerAdapter);

        return orderPagerAdapter;
    }

    private void addSubmittedRequestsFragment(OrderPagerAdapter orderPagerAdapter) {
        orderPagerAdapter.addFragment(new SubmittedRequestsFragment(), getString(R.string.section_header_submitted_orders));
    }

    private void addUnsubmittedRequestsFragment(OrderPagerAdapter orderPagerAdapter) {
        if (areThereUnsubmittedRequests()) {
            orderPagerAdapter.addFragment(new UnsubmittedRequestsFragment(), getString(R.string.section_header_unsubmitted_orders));
        }
    }

    private boolean areThereUnsubmittedRequests() {
        return !dataManager.getUnsubmittedRequests().isEmpty();
    }

    private void goToCreateRequestsActivity() {
        Intent intent = new Intent(this, CreateRequestActivity.class);
        startActivity(intent);
    }
}
