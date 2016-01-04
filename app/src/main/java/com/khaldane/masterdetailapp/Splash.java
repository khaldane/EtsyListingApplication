package com.khaldane.masterdetailapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khaldane.masterdetailapp.EndpointContainers.ListingDetailsDisplay;
import com.khaldane.masterdetailapp.GlobalClasses.EtsyService;


public class Splash extends AppCompatActivity {

    private AsyncTask featuredTask, trendingTask, activeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Set the theme
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        //Check if the user has internet
        if(isNetworkAvailable()) {
            //Run asynctasks simultaneously
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                trendingTask = new GetTrendingListings().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                activeTask = new GetActiveListings().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                featuredTask = new GetFeaturedListings().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                featuredTask = new GetFeaturedListings().execute();
                trendingTask = new GetTrendingListings().execute();
                activeTask = new GetActiveListings().execute();
            }
        } else {
            TextView tvLoading = (TextView) findViewById(R.id.tvLoading);
            tvLoading.setText("PLEASE TURN NETWORK ON TO VIEW LISTINGS");

            ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
            pbLoading.setVisibility(View.GONE);

            LinearLayout llLoadingBody = (LinearLayout) findViewById(R.id.llLoadingBody);
            llLoadingBody.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*
     * Gets the features listings from Etsy
     */
    private class GetFeaturedListings extends AsyncTask<Void, String, ListingDetailsDisplay> {

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            return EtsyService.getFeaturedListings(1, Splash.this);
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {
            updateLoadingStatus(true
                    , activeTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , trendingTask.getStatus().equals(AsyncTask.Status.FINISHED));
        }
    }

    /*
     * Gets the trending listings from Etsy
     */
    private class GetTrendingListings extends AsyncTask<Void, String, ListingDetailsDisplay> {

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            return EtsyService.getTrendingListings(1, Splash.this);
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {
            updateLoadingStatus(featuredTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , activeTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , true);
        }
    }

    /*
     * Gets the active listings from Etsy
     */
    private class GetActiveListings extends AsyncTask<Void, String, ListingDetailsDisplay> {

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            return EtsyService.getActiveListings(1, Splash.this);
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {
            updateLoadingStatus(featuredTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , true
                    , trendingTask.getStatus().equals(AsyncTask.Status.FINISHED));

        }
    }

    /*
     * Updates the loading status
     * @return Boolean, Boolean, Boolean
     */
    private void updateLoadingStatus(Boolean featured, Boolean trending, Boolean active) {
        if(featured && trending && active) {
            TextView tvLoading = (TextView) findViewById(R.id.tvLoading);
            tvLoading.setText("CLICK ANYWHERE TO GET STARTED");

            ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
            pbLoading.setVisibility(View.GONE);

            LinearLayout llLoadingBody = (LinearLayout) findViewById(R.id.llLoadingBody);
            llLoadingBody.setGravity(Gravity.CENTER|Gravity.BOTTOM);

            LinearLayout llLoading = (LinearLayout) findViewById(R.id.llLoading);
            llLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Splash.this, Main.class);
                    startActivity(intent);
                }
            });
        }
    }

    /*
     * Checks if the user has connection to the internet
     * @return Boolean
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
