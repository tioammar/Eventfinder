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
    private ImageView eventBarcode;

    private Uri dataUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        dataUri = Uri.parse(intent.getStringExtra("data_uri"));

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_light);
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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
        eventBarcode = (ImageView) findViewById(R.id.event_barcode);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

        String title = data.getString(Event.TITLE);
        collapsingToolbar.setTitle(title);

        eventContent.setText(data.getString(Event.CONTENT));

        Glide.with(this)
                .load(data.getString(Event.IMAGE))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(eventImage);

        String startDate = data.getString(Event.START_DATE);
        String endDate = data.getString(Event.END_DATE);

        // String startLocalTime = Utility.toLocalTime(startDate);
        // String endLocalTime = Utility.toLocalTime(endDate);

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
        int endDateinWeek = endCal.get(Calendar.DAY_OF_MONTH);
        int endYear = endCal.get(Calendar.YEAR);
        eventDate.setText("");

        eventLocation.setText(data.getString(Event.LOCATION));

        String price = getString(R.string.ticket_price).toUpperCase() + " " +
                data.getString(Event.PRICE).toUpperCase();
        eventPrice.setText(price);

        contactName.setText(data.getString(Event.CONTACT_NAME));
        contactAddress.setText(data.getString(Event.CONTACT_ADDRESS));
        contactTwitter.setText(data.getString(Event.CONTACT_TWITTER));
        contactFacebook.setText(data.getString(Event.CONTACT_FACEBOOK));
        contactLine.setText(data.getString(Event.CONTACT_LINE));

        Glide.with(this)
                .load(data.getString(Event.BARCODE))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(eventBarcode);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
