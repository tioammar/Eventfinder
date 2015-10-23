package inforuh.eventfinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by tioammar on 8/11/15.
 *
 */
public class Config {

    public static final String URL = "http://api.eventfinder.co.id/event/all";

    public static Date parseDate(String dateTime, TimeZone timeZone){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setTimeZone(timeZone);
        try {
            date = format.parse(dateTime);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }
}
