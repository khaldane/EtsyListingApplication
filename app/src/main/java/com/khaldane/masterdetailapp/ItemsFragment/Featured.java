package com.khaldane.masterdetailapp.ItemsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.khaldane.masterdetailapp.Adapters.ItemDetailArrayAdapter;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.Utility;

import java.util.Arrays;

public class Featured extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_featured,container,false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String SHARED_PREFS = "com.khaldane.masterdetailapp";
        populateFeatured(Utility.parseListingDetails(getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE).getString("featured", "")));

    }

    private void populateFeatured(ListingDetails featured) {

        final TextView tvNoResults = (TextView) getActivity().findViewById(R.id.tvNoResults);
        final ListView lvFeatured = (ListView) getView().findViewById(R.id.lvFeatured);

        if(featured.getResults().length > 0) {
            lvFeatured.setVisibility(View.VISIBLE);
            tvNoResults.setVisibility(View.GONE);

            ItemDetailArrayAdapter featuredArrayAdapter = new ItemDetailArrayAdapter(getActivity(), R.layout.listview_item, Arrays.asList(featured.getResults()));
            lvFeatured.setItemsCanFocus(false);
            lvFeatured.setAdapter(featuredArrayAdapter);

            lvFeatured.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Go to detailed view
                }
            });
        } else {
            lvFeatured.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
        }
    }

}
