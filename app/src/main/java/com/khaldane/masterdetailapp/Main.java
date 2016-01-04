package com.khaldane.masterdetailapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.khaldane.masterdetailapp.Adapters.TabViewPagerAdapter;
import com.khaldane.masterdetailapp.SlidingTabs.TabLayout;


public class Main extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up actionbar
        ActionBar supportActionBar =getSupportActionBar();
        assert getSupportActionBar() != null;
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setIcon(R.mipmap.ic_launcher);
        supportActionBar.setElevation(0);

        populateTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //Listener for submitting search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(Main.this, Search.class);
                intent.putExtra("query", query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Search Text
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.text_secondary_gray));
        searchAutoComplete.setHint("Search for items on Etsy");
        searchAutoComplete.setTextAppearance(this, android.R.style.TextAppearance_Small);
        searchAutoComplete.setTextColor(getResources().getColor(R.color.text_primary_gray));

        //Search Button
        ImageView searchIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.user);
//
//        //Clear button
//        ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
//        searchClose.setImageResource(R.drawable.user);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void populateTabs() {
        TabLayout tabs;

        final TabViewPagerAdapter adapter;

        tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.viewpager);

        String[] title = {
                "Featured", "Trending", "Active"
        };

        String[] fragments = {
                "com.khaldane.masterdetailapp.ListingFragments.Listings",
                "com.khaldane.masterdetailapp.ListingFragments.Listings",
                "com.khaldane.masterdetailapp.ListingFragments.Listings"
        };

        adapter =  new TabViewPagerAdapter(getSupportFragmentManager(), title, 3, this, fragments);

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3); //Saves the tab data to make swiping smooth

        tabs.setCustomTabView(R.layout.tab_global, R.id.tabText);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setViewPager(pager);
    }

    /*
     * Gets the viewpager item
     * @return int
     */
    public int getViewPager() {
        return pager.getCurrentItem();
    }

}
