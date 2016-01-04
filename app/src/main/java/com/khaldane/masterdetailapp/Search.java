package com.khaldane.masterdetailapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.khaldane.masterdetailapp.ListingFragments.Listings;


public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        instantiateSearchFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

}
