package inforuh.eventfinder.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tioammar
 * on 10/7/15.
 */
public class EventsData implements Serializable {

	@SerializedName("array")
    public List<Event> events;
}
