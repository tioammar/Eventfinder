package inforuh.eventfinder.io;

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

    public static final String[] PROJECTION = {
            Contract.EventColumn.ID,
            Contract.EventColumn.TITLE,
            Contract.EventColumn.CONTENT,
            Contract.EventColumn.IMAGE,
            Contract.EventColumn.START_DATE,
            Contract.EventColumn.END_DATE,
            Contract.EventColumn.LOCATION,
            Contract.EventColumn.PRICE,
            Contract.EventColumn.CONTACT_NAME,
            Contract.EventColumn.CONTACT_ADDRESS,
            Contract.EventColumn.CONTACT_TWITTER,
            Contract.EventColumn.CONTACT_FACEBOOK,
            Contract.EventColumn.CONTACT_LINE,
            Contract.EventColumn.BARCODE,
            Contract.EventColumn.CATEGORY
    };

    public static final int ID = 0;
    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    public static final int IMAGE = 3;
    public static final int START_DATE = 4;
    public static final int END_DATE = 5;
    public static final int LOCATION = 6;
    public static final int PRICE = 7;
    public static final int CONTACT_NAME = 8;
    public static final int CONTACT_ADDRESS = 9;
    public static final int CONTACT_TWITTER = 10;
    public static final int CONTACT_FACEBOOK = 11;
    public static final int CONTACT_LINE = 12;
    public static final int BARCODE = 13;
    public static final int CATEGORY = 14;
}
