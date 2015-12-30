package com.khaldane.masterdetailapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.khaldane.masterdetailapp.Adapters.TabViewPagerAdapter;
import com.khaldane.masterdetailapp.SlidingTabs.TabLayout;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Viewpager
        TabLayout tabs;

        //hide the actionbar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final ViewPager pager;
        final TabViewPagerAdapter adapter;

        tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.viewpager);

        String[] title = {
                "Featured", "Trending", "Active"
        };

        String[] fragments = {
                "com.khaldane.masterdetailapp.ItemsFragment.Featured",
                "com.khaldane.masterdetailapp.ItemsFragment.Trending",
                "com.khaldane.masterdetailapp.ItemsFragment.Active"
        };

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new TabViewPagerAdapter(getSupportFragmentManager(), title, 3, this, fragments);

        //Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        //Saves the tab data to make swiping smooth
        pager.setOffscreenPageLimit(3);

        tabs.setCustomTabView(R.layout.tab_global, R.id.tabText);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        //Get the bundle
        Bundle extras = getIntent().getExtras();
        String featured = extras.getString("featured", "");
        String trending = extras.getString("trending", "");
        String active = extras.getString("active", "");

        //Populate Listviews

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
