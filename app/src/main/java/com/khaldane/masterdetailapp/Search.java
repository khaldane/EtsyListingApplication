package com.khaldane.masterdetailapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khaldane.masterdetailapp.ListingFragments.Listings;


public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setUpActionbar();

        instantiateSearchFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem mReturn = menu.findItem(R.id.action_search_result);
        View actionView = mReturn.getActionView();

        TextView tvQuery = (TextView) actionView.findViewById(R.id.tvQuery);
        ImageView ivReturnArrow = (ImageView) actionView.findViewById(R.id.ivReturnArrow);

        Bundle extras = getIntent().getExtras();
        String query = extras.getString("query", "");

        tvQuery.setText(query);

        ivReturnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    /*
     * Sets up the search fragment
     */
    private void instantiateSearchFragment() {
        //Get the bundle
        Bundle extras = getIntent().getExtras();
        String query = extras.getString("query", "");

        //Set the new bundle
        Bundle args = new Bundle();
        args.putString("query", query);
        args.putBoolean("search", true);

        Fragment fragment = new Listings();
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.llSearch, fragment);
        fragmentTransaction.commit();
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

}
