package com.khaldane.masterdetailapp.ListingFragments;

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
import com.khaldane.masterdetailapp.Main;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.EtsyService;
import com.khaldane.masterdetailapp.Utility;

public class Listings extends Fragment {

    private int currentPage = 1;
    private boolean loadingEnd = false;
    private ItemDetailArrayAdapter listingAdapter;
    private ListingDetailsDisplay listing;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listings,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateListings();

        handlers();
    }

    /*
     * Populate the listings gridview
     */
    private void populateListings() {
        String SHARED_PREFS = "com.khaldane.masterdetailapp";

        final TextView tvNoResults = (TextView) getView().findViewById(R.id.tvNoResults);
        final GridView gvListings = (GridView) getView().findViewById(R.id.gvListings);

        //Get the viewpage
        Bundle bundle = this.getArguments();
        boolean search = bundle.getBoolean("search", false);
        int position = bundle.getInt("position", 0);

        //Determine if it is being called from tab view or search view
        if(search) {
            String query = bundle.getString("query", "");
            listing = EtsyService.getSearchQuery(1, query);
        } else {
            String[] type = { "featured", "trending", "active"};
            listing = Utility.parseListingDetails(getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE).getString(type[position], ""));
        }

        //Populate the gridview
        if(listing.getResults().size() > 0) {
            gvListings.setVisibility(View.VISIBLE);
            tvNoResults.setVisibility(View.GONE);

            listingAdapter = new ItemDetailArrayAdapter(getActivity(), R.layout.listview_item, listing.getResults());
            gvListings.setAdapter(listingAdapter);

            //Listing onclick listener
            gvListings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Gson gson = Utility.gsonBuilder();

                    Intent intent = new Intent(getActivity(), ItemDetails.class);
                    intent.putExtra("itemDetails", gson.toJson(listing.getResults().get(position)));
                    startActivity(intent);
                }
            });

        } else {
            gvListings.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
        }
    }


    /*
     * Handlers
     */
    private void handlers() {
        //Swipe container refresh listener
        final SwipeRefreshLayout scListings = (SwipeRefreshLayout) getView().findViewById(R.id.scListings);
        scListings.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetListings().execute();
            }
        });

        //GridView on scroll listner
        final GridView gvListings = (GridView) getView().findViewById(R.id.gvListings);
        gvListings.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                int topRowVerticalPosition = (gvListings == null || gvListings.getChildCount() == 0) ? 0 : gvListings.getChildAt(0).getTop();
                int vH = view.getHeight();

                //Swiped to top of the gridview
                scListings.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);

                //Swiped to the bottom of gridview
                if(firstVisibleItem + visibleItemCount == totalItemCount){
                    int bottomPos = view.getChildAt(visibleItemCount - 1).getBottom();
                    if (!loadingEnd && vH >= bottomPos) {
                        loadingEnd = true;
                        new GetMoreListings().execute();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });
    }

    /*
     * Refresh listings
     */
    class GetListings extends AsyncTask<Void, String, ListingDetailsDisplay> {

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            Main main = (Main) getView().getContext();
            int position = main.getViewPager();

            //Determine which view tab view is active
            switch(position) {
                case 1:
                    return EtsyService.getTrendingListings(currentPage, getActivity());
                case 2:
                    return EtsyService.getActiveListings(currentPage, getActivity());
                default:
                    return EtsyService.getFeaturedListings(currentPage, getActivity());
            }
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {
            //Reset the listing array list
            SwipeRefreshLayout scListings = (SwipeRefreshLayout) getView().findViewById(R.id.scListings);
            listing.getResults().clear();
            listing.getResults().addAll(results.getResults());

            //Refresh the grid view
            GridView gvListings = (GridView) getView().findViewById(R.id.gvListings);
            gvListings.invalidateViews();

            currentPage = 1;
            scListings.setRefreshing(false);
        }
    }

    /*
     * Get more listings
     */
    class GetMoreListings extends AsyncTask<Void, String, ListingDetailsDisplay> {
        final ProgressBar pbLoadingListings = (ProgressBar) getView().findViewById(R.id.pbLoadingListings);

        @Override
        protected void onPreExecute() {
            pbLoadingListings.setVisibility(View.VISIBLE);
        }

        @Override
        protected ListingDetailsDisplay doInBackground(Void... params) {
            currentPage = currentPage + 1;
            Main main = (Main) getView().getContext();
            int position = main.getViewPager();

            //Determine which view tab view is active
            switch(position) {
                case 1:
                    return EtsyService.getTrendingListings(currentPage, getActivity());
                case 2:
                    return EtsyService.getActiveListings(currentPage, getActivity());
                default:
                    return EtsyService.getFeaturedListings(currentPage, getActivity());
            }
        }

        @Override
        protected void onPostExecute(ListingDetailsDisplay results) {
            //Add listings to the listing array list
            listing.getResults().addAll(results.getResults());
            //Refresh the grid view
            listingAdapter.refresh(listing.getResults());
            loadingEnd = false;
            pbLoadingListings.setVisibility(View.GONE);
            //Hide the swipe loading
            SwipeRefreshLayout scListings = (SwipeRefreshLayout) getView().findViewById(R.id.scListings);
            scListings.setRefreshing(false);
        }
    }
}