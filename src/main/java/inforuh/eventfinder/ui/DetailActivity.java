/*
 * Copyright 2015 Aditya Amirullah. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package inforuh.eventfinder.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import inforuh.eventfinder.Config;
import inforuh.eventfinder.R;
import inforuh.eventfinder.io.Event;

/**
 * Created by tioammar
 * on 10/7/15.
 */
public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private CollapsingToolbarLayout collapsingToolbar;
    private TextView eventTitle;
    private TextView eventContent;
    private ImageView eventImage;
    private TextView eventDate;
    private TextView eventPrice;
    private TextView eventLocation;

    private TextView contactName;
    private TextView contactAddress;
    private TextView contactTwitter;
    private TextView contactFacebook;
    private TextView contactLine;
    private TextView contactInstagram;
    private TextView contactPath;
    private ImageView eventBarcode;
    private GoogleMap googleMap;

    private Uri dataUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        dataUri = intent.getData();

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_light);
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        eventTitle = (TextView) findViewById(R.id.event_title_detail);
        eventContent = (TextView) findViewById(R.id.event_content_detail);
        eventImage = (ImageView) findViewById(R.id.event_image_detail);
        eventDate = (TextView) findViewById(R.id.event_date_detail);
        eventPrice = (TextView) findViewById(R.id.event_price_detail);
        eventLocation = (TextView) findViewById(R.id.event_location_detail);

        contactName = (TextView) findViewById(R.id.contact_name);
        contactAddress = (TextView) findViewById(R.id.contact_address);
        contactTwitter = (TextView) findViewById(R.id.contact_twitter);
        contactFacebook = (TextView) findViewById(R.id.contact_facebook);
        contactLine = (TextView) findViewById(R.id.contact_line);
        contactInstagram = (TextView) findViewById(R.id.contact_instagram);
        contactPath = (TextView) findViewById(R.id.contact_path);
        eventBarcode = (ImageView) findViewById(R.id.event_barcode);

        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        googleMap = map.getMap();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleMap.clear();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                dataUri,
                Event.PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null){
            return;
        }

        if (data.moveToFirst()) {
            String title = data.getString(Event.TITLE);
            collapsingToolbar.setTitle("");
            eventTitle.setText(title);
            eventContent.setText(data.getString(Event.CONTENT));

            Glide.with(this)
                    .load(data.getString(Event.IMAGE))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(eventImage);

            String startDate = data.getString(Event.START_DATE);
            String endDate = data.getString(Event.END_DATE);

            Date startDateFormat = Config.parseDate(startDate, TimeZone.getDefault());
            Date endDateFormat = Config.parseDate(endDate, TimeZone.getDefault());

            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            startCal.setTime(startDateFormat);
            endCal.setTime(endDateFormat);

            int startMonth = startCal.get(Calendar.MONTH);
            int startDateInWeek = startCal.get(Calendar.DAY_OF_MONTH);
            int startYear = startCal.get(Calendar.YEAR);

            int endMonth = endCal.get(Calendar.MONTH);
            int endDateInWeek = endCal.get(Calendar.DAY_OF_MONTH);
            int endYear = endCal.get(Calendar.YEAR);

            String formattedStartDate = Config.formatDate(this, startDateInWeek) + " " +
                    Config.formatMonth(this, startMonth) + " " + startYear;
            String formattedEndDate = Config.formatDate(this, endDateInWeek) + " " +
                    Config.formatMonth(this, endMonth) + " " + endYear;
            String completeDate = formattedStartDate + " - " + formattedEndDate;
            eventDate.setText(completeDate);

            String location = "Makassar, Indonesia";
            eventLocation.setText(location);

            String price = getString(R.string.ticket_price).toUpperCase() + " " +
                    data.getString(Event.PRICE).toUpperCase();
            eventPrice.setText(price);

            String name = "Event Finder";
            contactName.setText(name);

            String address = "Makassar, Indonesia";
            contactAddress.setText(address);

            String twitter = "Twitter: " + data.getString(Event.CONTACT_TWITTER);
            contactTwitter.setText(twitter);

            String facebook = "Facebook: " + data.getString(Event.CONTACT_FACEBOOK);
            contactFacebook.setText(facebook);

            String line = "Line: " + data.getString(Event.CONTACT_LINE);
            contactLine.setText(line);

            String instagram = "Instagram: " + data.getString(Event.CONTACT_INSTAGRAM);
            contactInstagram.setText(instagram);

            String path = "Path: " + data.getString(Event.CONTACT_PATH);
            contactPath.setText(path);

            Glide.with(this)
                    .load(data.getString(Event.BARCODE))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(eventBarcode);

            if (googleMap != null){
                setUpEventLocation(data.getDouble(Event.LONGITUDE),
                        data.getDouble(Event.LATITUDE));
            }
        }
    }

    private void setUpEventLocation(double longitude, double latitude) {
        if (longitude == 0 && latitude == 0) return;
        LatLng position = new LatLng(latitude, longitude);
        MarkerOptions opt = new MarkerOptions()
                .title("Event location")
                .position(position);
        googleMap.addMarker(opt);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14.0f));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
