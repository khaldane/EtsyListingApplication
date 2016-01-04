package com.khaldane.masterdetailapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khaldane.masterdetailapp.EndpointContainers.Results;
import com.khaldane.masterdetailapp.GlobalClasses.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        String itemDetails = bundle.getString("itemDetails", "");

        if(!itemDetails.equals("")) {
            populateActivity(itemDetails);
        } else {
            Toast.makeText(this, "Error obtaining item details",
                    Toast.LENGTH_LONG).show();
        }

        handlers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /*
     * Populates the activity
     */
    private void populateActivity(String itemDetails) {

        final Results item = Utility.parseResults(itemDetails);

        //Populate general details
        final ImageView ivItemImg = (ImageView) findViewById(R.id.ivItemImg);
        TextView tvItemTitle = (TextView) findViewById(R.id.tvItemTitle);
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);

        new Thread(new Runnable() {
            public void run() {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(item.getMainImage().getUrl_570xN()).getContent());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            ivItemImg.setImageBitmap(bitmap);
                        }
                    });

                } catch (IOException e) {
                    Log.d("bitmapError", e.toString());
                }
            }

        }).start();

        tvItemTitle.setText(Html.fromHtml(item.getTitle()));
        tvPrice.setText("$" + item.getPrice());

        //Populate overview
        TextView tvTitleBody = (TextView) findViewById(R.id.tvTitleBody);
        TextView tvQuantityNum = (TextView) findViewById(R.id.tvQuantityNum);
        TextView tvFavoriteNum = (TextView) findViewById(R.id.tvFavoriteNum);
        TextView tvViewNum = (TextView) findViewById(R.id.tvViewNum);
        TextView tvTagBody = (TextView) findViewById(R.id.tvTagBody);
        TextView tvMaterialBody = (TextView) findViewById(R.id.tvMaterialBody);

        tvTitleBody.setText(item.getTitle());
        tvQuantityNum.setText(Integer.toString(item.getQuantity()));
        tvFavoriteNum.setText(Integer.toString(item.getNum_favorers()));
        tvViewNum.setText(Integer.toString(item.getViews()));
        tvTagBody.setText(Html.fromHtml(Utility.parseArray(item.getTags())));
        tvMaterialBody.setText(Html.fromHtml(Utility.parseArray(item.getMaterials())));

        //Populate item description
        TextView tvItemDescBody = (TextView) findViewById(R.id.tvItemDescBody);
        tvItemDescBody.setText(Html.fromHtml(item.getDescription()));
    }

    /*
     * Handlers
     */
    private void handlers() {

        RelativeLayout rlOverviewBtn = (RelativeLayout) findViewById(R.id.rlOverviewBtn);
        RelativeLayout rlItemDescBtn = (RelativeLayout) findViewById(R.id.rlItemDescBtn);

        final RelativeLayout rlOverviewAccent = (RelativeLayout) findViewById(R.id.rlOverviewAccent);
        final RelativeLayout rlItemDescAccent = (RelativeLayout) findViewById(R.id.rlItemDescAccent);

        final TextView tvOverviewHeader = (TextView) findViewById(R.id.tvOverviewHeader);
        final TextView tvItemDescHeader = (TextView) findViewById(R.id.tvItemDescHeader);

        final RelativeLayout rlOverview = (RelativeLayout) findViewById(R.id.rlOverview);
        final TextView tvItemDescBody = (TextView) findViewById(R.id.tvItemDescBody);

        //OverView Button
        rlOverviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlOverviewAccent.setVisibility(View.VISIBLE);
                rlItemDescAccent.setVisibility(View.GONE);

                tvOverviewHeader.setTypeface(Typeface.DEFAULT_BOLD);
                tvItemDescHeader.setTypeface(Typeface.DEFAULT);

                tvOverviewHeader.setTextColor(getResources().getColor(R.color.black));
                tvItemDescHeader.setTextColor(getResources().getColor(R.color.text_primary_gray));

                rlOverview.setVisibility(View.VISIBLE);
                tvItemDescBody.setVisibility(View.GONE);
            }
        });

        //Item Description Button
        rlItemDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlOverviewAccent.setVisibility(View.GONE);
                rlItemDescAccent.setVisibility(View.VISIBLE);

                tvOverviewHeader.setTypeface(Typeface.DEFAULT);
                tvItemDescHeader.setTypeface(Typeface.DEFAULT_BOLD);

                tvOverviewHeader.setTextColor(getResources().getColor(R.color.text_primary_gray));
                tvItemDescHeader.setTextColor(getResources().getColor(R.color.black));

                rlOverview.setVisibility(View.GONE);
                tvItemDescBody.setVisibility(View.VISIBLE);
            }
        });
        final LinearLayout llReturn = (LinearLayout) findViewById(R.id.llReturn);

        llReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ScaleAnimation shrinkAnim = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f);
                ImageView ivReturnArrow = (ImageView) findViewById(R.id.ivReturnArrow);

                shrinkAnim.setDuration(100);
                ivReturnArrow.startAnimation(shrinkAnim);

                shrinkAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        onBackPressed();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

}
