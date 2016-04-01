package gov.peacecorps.medlinkandroid.ui.activities.requestslist;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.RequestsListFragment;

public class OrderPagerAdapter extends FragmentPagerAdapter {
    private final List<RequestsListFragment> mFragments = new LinkedList<>();
    private final List<String> mFragmentTitles = new LinkedList<>();

    public OrderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public RequestsListFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public void addFragment(RequestsListFragment fragment, String fragmentTitle){
        mFragments.add(fragment);
        mFragmentTitles.add(fragmentTitle);
    }
}
