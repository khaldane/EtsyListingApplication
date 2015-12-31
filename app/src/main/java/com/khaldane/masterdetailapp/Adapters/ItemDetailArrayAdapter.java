package com.khaldane.masterdetailapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.khaldane.masterdetailapp.EndpointContainers.Results;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        Lead Wrapper = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            Wrapper = new Lead();
//            Wrapper.date = (TextView) item.findViewById(R.id.tvDate);
//            Wrapper.time = (TextView) item.findViewById(R.id.tvTimInMin);
//            Wrapper.activity = (TextView) item.findViewById(R.id.tvActivity);
            item.setTag(Wrapper);
        } else {
            Wrapper = (Lead) item.getTag();
        }

        Results a = results.get(position);
        //Wrapper.activity.setText("None");

        return item;
    }

    static class Lead {
//        TextView date;
//        TextView time;
//        TextView activity;
    }
}
