package inforuh.eventfinder.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import inforuh.eventfinder.provider.Contract;

/**
 * Created by tioammar
 * on 8/14/15.
 *
 * Event model
 */
public class Event implements Serializable {

    public Event() {
    }

    @SerializedName("arId") public int id;
    @SerializedName("arTitle") public String title;
    @SerializedName("arAuthor") public int author;
    @SerializedName("arDateStart") public String dateStart;
    @SerializedName("arDateEnd") public String endDate;
    @SerializedName("arContent") public String content;
    @SerializedName("arBarcode") public String barcode;
    @SerializedName("arPict") public String picture;
    @SerializedName("arTicketPrice") public String price;
    @SerializedName("arURL") public String url;
    @SerializedName("catName") public String category;
    @SerializedName("amName") public String admin;
    @SerializedName("mapLongitude") public double longitude;
    @SerializedName("mapLatitude") public double latitude;
    @SerializedName("mapZoom") public float zoom;
    @SerializedName("smTwitter") public String twitter;
    @SerializedName("smFacebook") public String facebook;
    @SerializedName("smInstagram") public String instagram;
    @SerializedName("smLine") public String line;
    @SerializedName("smPath") public String path;

    public static final String[] PROJECTION = {
            Contract.EventColumn.ID,
            Contract.EventColumn.TITLE,
            Contract.EventColumn.CONTENT,
            Contract.EventColumn.IMAGE,
            Contract.EventColumn.START_DATE,
            Contract.EventColumn.END_DATE,
            Contract.EventColumn.LONGITUDE,
            Contract.EventColumn.LATITUDE,
            Contract.EventColumn.PRICE,
            Contract.EventColumn.CONTACT_NAME,
            Contract.EventColumn.CONTACT_ADDRESS,
            Contract.EventColumn.CONTACT_TWITTER,
            Contract.EventColumn.CONTACT_FACEBOOK,
            Contract.EventColumn.CONTACT_LINE,
            Contract.EventColumn.CONTACT_INSTAGRAM,
            Contract.EventColumn.CONTACT_PATH,
            Contract.EventColumn.BARCODE,
            Contract.EventColumn.CATEGORY,
            Contract.EventColumn.URL,
            Contract.EventColumn.LOCATION
    };

    public static final int ID = 0;
    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    public static final int IMAGE = 3;
    public static final int START_DATE = 4;
    public static final int END_DATE = 5;
    public static final int LONGITUDE = 6;
    public static final int LATITUDE = 7;
    public static final int PRICE = 8;
    public static final int CONTACT_NAME = 9;
    public static final int CONTACT_ADDRESS = 10;
    public static final int CONTACT_TWITTER = 11;
    public static final int CONTACT_FACEBOOK = 12;
    public static final int CONTACT_LINE = 13;
    public static final int CONTACT_INSTAGRAM = 14;
    public static final int CONTACT_PATH = 15;
    public static final int BARCODE = 16;
    public static final int CATEGORY = 17;
    public static final int URL = 18;
    public static final int LOCATION = 19;
}
