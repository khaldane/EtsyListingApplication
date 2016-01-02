package com.khaldane.masterdetailapp.ItemsFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.khaldane.masterdetailapp.Adapters.ItemDetailArrayAdapter;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetailsDisplay;
import com.khaldane.masterdetailapp.ItemDetails;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.RetrofitCalls;
import com.khaldane.masterdetailapp.Utility;

public class Featured extends Fragment {

    private int currentPage = 1;
    private boolean loadingStart = false;
    private boolean loadingEnd = false;
    ItemDetailArrayAdapter featuredAdapter;
    ListingDetailsDisplay featured;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_featured,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        populateFeatured();

        handlers();
    }

    private void populateFeatured() {
        String SHARED_PREFS = "com.khaldane.masterdetailapp";

        final TextView tvNoResults = (TextView) getActivity().findViewById(R.id.tvNoResults);
        final GridView gvFeatured = (GridView) getView().findViewById(R.id.gvFeatured);

        featured = Utility.parseListingDetails(getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE).getString("featured", ""));

        if (featured.getResults().size() > 0) {
            gvFeatured.setVisibility(View.VISIBLE);
            tvNoResults.setVisibility(View.GONE);

            featuredAdapter = new ItemDetailArrayAdapter(getActivity(), R.layout.listview_item, featured.getResults());
            gvFeatured.setAdapter(featuredAdapter);

            gvFeatured.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Go to detailed view
                    Gson gson = Utility.gsonBuilder();

                    Intent intent = new Intent(getActivity(), ItemDetails.class);
                    intent.putExtra("itemDetails", gson.toJson(featured.getResults().get(position)));
                    startActivity(intent);
                }
            });

        } else {
            gvFeatured.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
        }
    }

    private void handlers() {
        final GridView gvFeatured = (GridView) getView().findViewById(R.id.gvFeatured);
        gvFeatured.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                int topRowVerticalPosition = (gvFeatured == null || gvFeatured.getChildCount() == 0) ? 0 : gvFeatured.getChildAt(0).getTop();
                int vH = view.getHeight();

                //Swiped to top of the gridview
                if(firstVisibleItem == 0 && topRowVerticalPosition >= 0) {
                    if(!loadingStart) {
                        loadingStart = true;
                        //new GetFeaturedListings().execute();
                    }
                }

                //Swiped to the bottom of gridview
                if(firstVisibleItem + visibleItemCount == totalItemCount){
                    int bottomPos = view.getChildAt(visibleItemCount - 1).getBottom();
                    if(!loadingEnd && vH >= bottomPos) {
                        loadingEnd = true;
                        new GetMoreFeaturedListings().execute();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });
    }

    class GetFeaturedListings extends AsyncTask<Void, String, ListingDetailsDisplay> {

        final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);

        @Override
        protected void onPreExecute() {
            swipeContainer.setEnabled(true);
        }

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            return RetrofitCalls.getFeaturedListings(1, getActivity());
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {
            featuredAdapter.refresh(results.getResults());
            currentPage = 1;
            loadingStart = false;
            swipeContainer.setRefreshing(false);
        }
    }

    class GetMoreFeaturedListings extends AsyncTask<Void, String, ListingDetailsDisplay> {

        final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        final ProgressBar pbLoadingFeatures = (ProgressBar) getView().findViewById(R.id.pbLoadingFeatures);

        @Override
        protected void onPreExecute() {
            pbLoadingFeatures.setVisibility(View.VISIBLE);
        }

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            currentPage = currentPage + 1;
            return RetrofitCalls.getFeaturedListings(currentPage, getActivity());
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {

            featured.getResults().addAll(results.getResults());

            featuredAdapter.refresh(featured.getResults());
            loadingEnd = false;
            pbLoadingFeatures.setVisibility(View.GONE);
            swipeContainer.setRefreshing(false);
        }
    }
}
