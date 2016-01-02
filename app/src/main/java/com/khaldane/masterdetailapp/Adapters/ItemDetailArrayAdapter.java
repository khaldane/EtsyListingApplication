package com.khaldane.masterdetailapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.khaldane.masterdetailapp.EndpointContainers.Results;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.ShopDetails;

import java.util.List;

public class ItemDetailArrayAdapter extends ArrayAdapter<Results> {
    Context context;
    int layoutResourceId;
    List<Results> results;

    public ItemDetailArrayAdapter(Context context, int layoutResourceId, List<Results> r) {
        super(context, layoutResourceId, r);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.results = r;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        Lead Wrapper = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            Wrapper = new Lead();
            Wrapper.title = (TextView) item.findViewById(R.id.tvItemTitle);
            Wrapper.price = (TextView) item.findViewById(R.id.tvPrice);
            Wrapper.shop = (RelativeLayout) item.findViewById(R.id.rlUserShop);
            item.setTag(Wrapper);
        } else {
            Wrapper = (Lead) item.getTag();
        }

        Results a = results.get(position);

        try {
            Wrapper.title.setText(Html.fromHtml(a.getTitle()));
            Wrapper.price.setText("$" + a.getPrice());


            Wrapper.shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopDetails.class);
                    intent.putExtra("userId", position);
                    context.startActivity(intent);
                }
            });
        } catch (NullPointerException ex) {}

        return item;
    }

    public void refresh(List<Results> r) {
        this.results = r;
        notifyDataSetChanged();
    }

    static class Lead {
        TextView title;
        TextView price;
        RelativeLayout shop;
    }
}
