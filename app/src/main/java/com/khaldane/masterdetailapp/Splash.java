package com.khaldane.masterdetailapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;


public class Splash extends AppCompatActivity {

    AsyncTask featuredTask, trendingTask, activeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
            Toast.makeText(this, "Please turn network connection on to view listings",
                    Toast.LENGTH_LONG).show();

            updateLoadingStatus(true, true, true);
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
            return RetrofitCalls.getFeaturedListings(1, Splash.this);
        }

        @Override
        protected void onPostExecute(ListingDetails results) {

            updateLoadingStatus(true
                    , activeTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , trendingTask.getStatus().equals(AsyncTask.Status.FINISHED));
        }
    }

    class GetTrendingListings extends AsyncTask<Void, String, ListingDetails> {

        @Override
        protected ListingDetails doInBackground(Void... params) {
            return RetrofitCalls.getTrendingListings(1, Splash.this);
        }

        @Override
        protected void onPostExecute(ListingDetails results) {

            updateLoadingStatus(featuredTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , activeTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , true);
        }
    }

    class GetActiveListings extends AsyncTask<Void, String, ListingDetails> {

        @Override
        protected ListingDetails doInBackground(Void... params) {
            return RetrofitCalls.getActiveListings(1, Splash.this);
        }

        @Override
        protected void onPostExecute(ListingDetails results) {

            updateLoadingStatus(featuredTask.getStatus().equals(AsyncTask.Status.FINISHED)
                    , true
                    , trendingTask.getStatus().equals(AsyncTask.Status.FINISHED));

        }
    }

    private void updateLoadingStatus(Boolean featured, Boolean trending, Boolean active) {
        if(featured && trending && active) {
            TextView tvLoading = (TextView) findViewById(R.id.tvLoading);
            tvLoading.setText("GET STARTED");

            ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
            pbLoading.setVisibility(View.GONE);

            ImageView ivGetStartedArrow = (ImageView) findViewById(R.id.ivGetStartedArrow);
            ivGetStartedArrow.setVisibility(View.VISIBLE);

            arrowAnimation();

            RelativeLayout rlLoading = (RelativeLayout) findViewById(R.id.rlLoading);
            rlLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Splash.this, Main.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void arrowAnimation() {
        final ImageView ivGetStartedArrow = (ImageView) findViewById(R.id.ivGetStartedArrow);
        final Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide);

        slideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slideAnimation.setAnimationListener(this);
                slideAnimation.setStartOffset(300);
                ivGetStartedArrow.startAnimation(slideAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivGetStartedArrow.startAnimation(slideAnimation);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
