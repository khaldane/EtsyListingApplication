package com.khaldane.masterdetailapp.ItemsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.khaldane.masterdetailapp.Adapters.ItemDetailArrayAdapter;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;
import com.khaldane.masterdetailapp.ItemDetails;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.Utility;

import java.util.Arrays;

public class Featured extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_featured,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String SHARED_PREFS = "com.khaldane.masterdetailapp";
        populateFeatured(Utility.parseListingDetails(getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE).getString("featured", "")));

    }

    private void populateFeatured(final ListingDetails featured) {

        final TextView tvNoResults = (TextView) getActivity().findViewById(R.id.tvNoResults);
        final GridView gvFeatured = (GridView) getView().findViewById(R.id.gvFeatured);

        if(featured.getCount() > 0) {
            gvFeatured.setVisibility(View.VISIBLE);
            tvNoResults.setVisibility(View.GONE);

            gvFeatured.setAdapter(new ItemDetailArrayAdapter(getActivity(), R.layout.listview_item, Arrays.asList(featured.getResults())));;

            gvFeatured.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Go to detailed view
                    Gson gson = Utility.gsonBuilder();

                    Intent intent = new Intent(getActivity(), ItemDetails.class);
                    intent.putExtra("itemDetails", gson.toJson(featured.getResults()[position]));
                    startActivity(intent);
                }
            });

        } else {
            gvFeatured.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
        }
    }

}
