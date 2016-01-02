package com.khaldane.masterdetailapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.khaldane.masterdetailapp.EndpointContainers.Results;
import com.khaldane.masterdetailapp.R;
import com.khaldane.masterdetailapp.ShopDetails;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
            Wrapper.mainImage = (ImageView) item.findViewById(R.id.tvMainImage);
            Wrapper.title = (TextView) item.findViewById(R.id.tvItemTitle);
            Wrapper.price = (TextView) item.findViewById(R.id.tvPrice);
            Wrapper.shop = (RelativeLayout) item.findViewById(R.id.rlUserShop);
            item.setTag(Wrapper);
        } else {
            Wrapper = (Lead) item.getTag();
        }

        final Results a = results.get(position);

        final Lead finalWrapper = Wrapper;

        new Thread(new Runnable() {
            public void run() {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(a.getMainImage().getUrl_170x135()).getContent());

                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        public void run() {
                            finalWrapper.mainImage.setImageBitmap(bitmap);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

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

        return item;
    }

    public void refresh(List<Results> r) {
        this.results = r;
        notifyDataSetChanged();
    }

    static class Lead {
        ImageView mainImage;
        TextView title;
        TextView price;
        RelativeLayout shop;
    }
}
