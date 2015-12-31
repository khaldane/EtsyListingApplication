package com.khaldane.masterdetailapp.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    String icons[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int count; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    String[] mFragments;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public TabViewPagerAdapter(FragmentManager fm, String[] mTitles, int mNumbOfTabsumb, Context c, String[] fragments) {
        super(fm);

        this.icons = mTitles;
        this.count = mNumbOfTabsumb;
        this.context = c;
        this.mFragments = fragments;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, mFragments[position]);
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return icons[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }
}