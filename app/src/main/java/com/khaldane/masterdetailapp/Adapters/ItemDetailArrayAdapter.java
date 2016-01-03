package com.khaldane.masterdetailapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khaldane.masterdetailapp.EndpointContainers.Results;
import com.khaldane.masterdetailapp.R;
import com.squareup.picasso.Picasso;

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
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.mainImage = (ImageView) convertView.findViewById(R.id.tvMainImage);
            holder.title = (TextView) convertView.findViewById(R.id.tvItemTitle);
            holder.price = (TextView) convertView.findViewById(R.id.tvPrice);
            convertView.setTag(holder);
        } else {
            //View has been recycled
            holder = (ViewHolder) convertView.getTag();

        }

        final Results a = results.get(position);

        Picasso.with(context).load(a.getMainImage().getUrl_170x135()).into(holder.mainImage);

        holder.title.setText(Html.fromHtml(a.getTitle()));
        holder.price.setText("$" + a.getPrice());

        return convertView;
    }

    public void refresh(List<Results> r) {
        this.results = r;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView mainImage;
        TextView title;
        TextView price;
    }
}
