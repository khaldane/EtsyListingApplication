package com.khaldane.masterdetailapp.ItemsFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.Utility;

public class Trending extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_trending,container,false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String SHARED_PREFS = "com.khaldane.masterdetailapp";
        populateTrending(Utility.parseListingDetails(getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE).getString("trending", "")));

    }

    private void populateTrending(ListingDetails trending) {

    }

}
