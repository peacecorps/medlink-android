package gov.peacecorps.medlinkandroid.adapters.requestslist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class OrderPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new LinkedList<>();
    private final List<String> mFragmentTitles = new LinkedList<>();

    public OrderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
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

    public void addFragment(Fragment fragment, String fragmentTitle){
        mFragments.add(fragment);
        mFragmentTitles.add(fragmentTitle);
    }
}
