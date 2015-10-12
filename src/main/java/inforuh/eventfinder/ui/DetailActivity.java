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

import inforuh.eventfinder.R;
import inforuh.eventfinder.io.Event;

/**
 * Created by tioammar
 * on 10/7/15.
 */
public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private CollapsingToolbarLayout collapsingToolbar;
//    private TextView eventTitle;
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
//        dataUri = Uri.parse(intent.getStringExtra("data_uri"));

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_light);
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        eventTitle = (TextView) findViewById(R.id.event_title_detail);
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

        String title = "Sample Event";
//        eventTitle.setText(title.toUpperCase());
        collapsingToolbar.setTitle(title);

        String content = "Lorem ipsum dolor sit amet, sed vero suscipiantur cu. Corpora eligendi sea ad, mel erat euismod id, dicant feugait sed id. Legimus accusata deterruisset vis an. An sit commune copiosae apeirian, vel quodsi volutpat ne, in sea blandit dignissim. Ei sit delenit imperdiet. Utamur mandamus duo ad, facer cetero scriptorem sea ad. Ea quo vide reprehendunt, omnium theophrastus usu ad, modo lorem ad per.\n" +
                "\n" +
                "Sapientem voluptaria definitionem ei eam, eos at soluta indoctum. Case similique eos no. Possim mentitum scripserit id sed, in gloriatur dissentias per, suas dolorem vivendo mea no. Pro ut legere theophrastus, usu stet nibh id.\n" +
                "\n" +
                "Eligendi suavitate te eos, ex stet essent sapientem eam, cu facer alterum alienum sea. Illud necessitatibus an vim, sit ei dicit tritani tibique. Id doctus aliquip sea, at mea exerci lobortis. Debet prompta reformidans ei eos, cu adipiscing constituam sed, pri eu inermis mentitum.\n" +
                "\n" +
                "Cu malorum pertinacia abhorreant sea. Cu vel saperet accumsan dissentias, disputationi definitiones et mei, patrioque repudiandae eu vim. Has interesset neglegentur consequuntur eu, elit torquatos ad vel, nec velit qualisque te. Id qui quodsi accumsan electram, per delenit invenire disputando id. Cum consequat voluptatum in.\n" +
                "\n" +
                "Per ut unum movet, graeci timeam ne vim. Ad duo adhuc omnium sensibus, ex vim quis alii aliquam, mei in unum quidam praesent. Ne ignota habemus ullamcorper pri, ullum apeirian verterem an eam, labitur impedit usu ad. Pri cu offendit scribentur mediocritatem, cu odio harum his, ut quodsi malorum has. Ex vivendo appetere eum. Mea ex enim posse cetero.";
        eventContent.setText(content);

        Glide.with(this)
                .load("https://pbs.twimg.com/profile_images/462501444977836032/G6h25qnI.jpeg")
                .centerCrop()
                .into(eventImage);

        String startDate = "5 September";
        String endDate = "6 September";
        eventDate.setText(startDate + " - " + endDate);

        String location = "Gedung Mulo, Makassar";
        eventLocation.setText(location);

        eventPrice.setText(getString(R.string.ticket_price).toUpperCase() + " IDR 100.000");

        contactName.setText("Aditya Amirullah");
        contactAddress.setText("BTN Minasa Upa, Makassar");
        contactTwitter.setText("Twitter : @tioammar");
        contactFacebook.setText("Facebook : Aditya Amirullah");
        contactLine.setText("Line : tioammar");

        Glide.with(this)
                .load("http://cdnqrcgde.s3-eu-west-1.amazonaws.com/wp-content/uploads/2013/11/jpeg.jpg")
                .centerCrop()
                .into(eventBarcode);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getSupportLoaderManager().initLoader(0, null, this);
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
//        eventTitle.setText(data.getString(Event.TITLE).toUpperCase());
        collapsingToolbar.setTitle(title);

        eventContent.setText(data.getString(Event.CONTENT));

        Glide.with(this)
                .load(data.getString(Event.IMAGE))
                .centerCrop()
                .into(eventImage);

        String startDate = data.getString(Event.START_DATE);
        String endDate = data.getString(Event.END_DATE);
        eventDate.setText(startDate + " - " + endDate);

        eventLocation.setText(data.getString(Event.LOCATION));

        eventPrice.setText(getString(R.string.ticket_price).toUpperCase() + " " +
                data.getString(Event.PRICE).toUpperCase());

        contactName.setText(data.getString(Event.CONTACT_NAME));
        contactAddress.setText(data.getString(Event.CONTACT_ADDRESS));
        contactTwitter.setText(data.getString(Event.CONTACT_TWITTER));
        contactFacebook.setText(data.getString(Event.CONTACT_FACEBOOK));
        contactLine.setText(data.getString(Event.CONTACT_LINE));

        Glide.with(this)
                .load(data.getString(Event.BARCODE))
                .centerCrop()
                .into(eventBarcode);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
