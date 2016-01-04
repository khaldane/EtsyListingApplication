package com.khaldane.masterdetailapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.khaldane.masterdetailapp.Adapters.TabViewPagerAdapter;
import com.khaldane.masterdetailapp.SlidingTabs.TabLayout;


public class Main extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpActionbar();

        populateTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mSearchbar = menu.findItem(R.id.action_search);
        View actionView = mSearchbar.getActionView();

        final EditText etSearch = (EditText) actionView.findViewById(R.id.etSearch);
        final ImageView ivSrcMag = (ImageView) actionView.findViewById(R.id.ivSrcMag);
        final ImageView ivClose = (ImageView) actionView.findViewById(R.id.ivClose);
        final ImageView ivLogo = (ImageView) actionView.findViewById(R.id.ivLogo);
        final ImageView ivSearchImg = (ImageView) actionView.findViewById(R.id.ivSearchImage);

        ivSearchImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ivSrcMag.setVisibility(View.VISIBLE);
                etSearch.setVisibility(View.VISIBLE);
                ivClose.setVisibility(View.VISIBLE);
                ivSearchImg.setVisibility(View.INVISIBLE);
                ivLogo.setVisibility(View.GONE);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSrcMag.setVisibility(View.GONE);
                etSearch.setVisibility(View.GONE);
                ivClose.setVisibility(View.GONE);
                ivSearchImg.setVisibility(View.VISIBLE);
                ivLogo.setVisibility(View.VISIBLE);
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String s = etSearch.getText().toString();

                    Intent intent = new Intent(Main.this,
                            Search.class);
                    intent.putExtra("query", s);
                    startActivity(intent);

                }
                return false;
            }
        });
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
     * Set up the actionbar
     */
    private void setUpActionbar() {
        ActionBar supportActionBar =getSupportActionBar();
        assert getSupportActionBar() != null;
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayShowHomeEnabled(false);
        supportActionBar.setElevation(0);
    }

    /*
     * Gets the viewpager item
     * @return int
     */
    public int getViewPager() {
        return pager.getCurrentItem();
    }

}
