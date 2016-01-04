package com.khaldane.masterdetailapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private String icons[];
    private int count;
    private String[] mFragments;

    /*
     * Constructor that assigns passed values to appropriate values in class
     * @params FragmentManager, String[], int, Context, String[]
     */
    public TabViewPagerAdapter(FragmentManager fm, String[] mTitles, int mNumbOfTabsNumb, Context c, String[] fragments) {
        super(fm);

        this.icons = mTitles;
        this.count = mNumbOfTabsNumb;
        this.context = c;
        this.mFragments = fragments;
    }

    /*
     * Instantiates the fragment for every position in view pager
     * @params int
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putBoolean("search", false);

        return Fragment.instantiate(context, mFragments[position], args);
    }

    /*
     * Gets title for tabs in TabStrip
     * @params int
     * @return CharSequence
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return icons[position];
    }

    /*
     * Gets number of tabs for TabStrip
     * @params int
     * @return int
     */
    @Override
    public int getCount() {
        return count;
    }

    /*
     * Gets tab position
     * @params Object
     * @return int
     */
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

}