package com.khaldane.masterdetailapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;


public class Splash extends AppCompatActivity {

    AsyncTask featuredTask, trendingTask, activeTask;

    ListingDetails featuredListings, trendingListings, activeListings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //hide the actionbar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

    class GetFeaturedListings extends AsyncTask<Void, String, ListingDetails> {

        @Override
        protected ListingDetails doInBackground(Void... params) {
            return RetrofitCalls.getFeaturedListings();
        }

        @Override
        protected void onPostExecute(ListingDetails results) {
            featuredListings = results;

            updateLoadingStatus(true
                    , activeTask.getStatus().equals(AsyncTask.Status.FINISHED) ? true : false
                    , trendingTask.getStatus().equals(AsyncTask.Status.FINISHED) ? true : false);
        }
    }

    class GetTrendingListings extends AsyncTask<Void, String, ListingDetails> {

        @Override
        protected ListingDetails doInBackground(Void... params) {
            return RetrofitCalls.getTrendingListings();
        }

        @Override
        protected void onPostExecute(ListingDetails results) {
            trendingListings = results;

            updateLoadingStatus(featuredTask.getStatus().equals(AsyncTask.Status.FINISHED) ? true : false
                    , activeTask.getStatus().equals(AsyncTask.Status.FINISHED) ? true : false
                    , true);
        }
    }

    class GetActiveListings extends AsyncTask<Void, String, ListingDetails> {

        @Override
        protected ListingDetails doInBackground(Void... params) {
            return RetrofitCalls.getActiveListings();
        }

        @Override
        protected void onPostExecute(ListingDetails results) {
            activeListings = results;

            updateLoadingStatus(featuredTask.getStatus().equals(AsyncTask.Status.FINISHED) ? true : false
                    , true
                    , trendingTask.getStatus().equals(AsyncTask.Status.FINISHED) ? true : false);

        }
    }

    private void updateLoadingStatus(Boolean featured, Boolean trending, Boolean active) {
        if(featured && trending && active) {
            TextView tvLoading = (TextView) findViewById(R.id.tvLoading);
            tvLoading.setText("GET STARTED");

            ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
            pbLoading.setVisibility(View.GONE);

            ImageView ivGetStarted = (ImageView) findViewById(R.id.ivGetStarted);
            ivGetStarted.setVisibility(View.VISIBLE);

            LinearLayout llLoading = (LinearLayout) findViewById(R.id.llLoading);
            llLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GsonBuilder builder = new GsonBuilder();
                    builder.serializeNulls();
                    Gson gson = builder.create();

                    Intent intent = new Intent(Splash.this, Main.class);
                    intent.putExtra("featured", gson.toJson(featuredListings));
                    intent.putExtra("trending", gson.toJson(trendingListings));
                    intent.putExtra("active", gson.toJson(activeListings));
                    startActivity(intent);
                }
            });
        }
    }
}
