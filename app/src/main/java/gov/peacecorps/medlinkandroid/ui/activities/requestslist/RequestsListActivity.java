package gov.peacecorps.medlinkandroid.ui.activities.requestslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.ui.activities.createrequest.CreateRequestActivity;
import gov.peacecorps.medlinkandroid.ui.activities.requestshistory.RequestsHistoryActivity;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.SubmittedRequestsFragment;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.unsubmittedrequests.UnsubmittedRequestsFragment;

public class RequestsListActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_LAST_FOCUSED_TAB = "EXTRA_LAST_FOCUSED_TAB";

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

    private int focusedTabPosition;

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
                goToRequestsHistoryActivity();
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
        initOrdersViewPager();
        ordersTabLayout.setupWithViewPager(ordersViewPager);
        ordersViewPager.setCurrentItem(focusedTabPosition);
        ordersViewPager.addOnPageChangeListener(this);
    }

    private void initOrdersViewPager() {
        ordersViewPager.setAdapter(buildOrderPagerAdapter());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_LAST_FOCUSED_TAB, focusedTabPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreLastFocusedTab(savedInstanceState);
    }

    private void restoreLastFocusedTab(Bundle savedInstanceState) {
        focusedTabPosition = savedInstanceState.getInt(EXTRA_LAST_FOCUSED_TAB);
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

    private void goToRequestsHistoryActivity() {
        Intent intent = new Intent(this, RequestsHistoryActivity.class);
        startActivity(intent);
    }

    private void goToCreateRequestsActivity() {
        Intent intent = new Intent(this, CreateRequestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        focusedTabPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
