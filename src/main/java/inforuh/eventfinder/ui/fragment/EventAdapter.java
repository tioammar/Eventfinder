package inforuh.eventfinder.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import inforuh.eventfinder.R;
import inforuh.eventfinder.io.Event;

/**
 * Created by tioammar
 * on 8/11/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    public interface Listener {
        void onClickListener(Uri uri);
    }

    private Cursor cursor;
    private Context context;
    private List<String> data;
    private Listener listener;

    public EventAdapter(Context context, List<String> data, Listener listener) {
        // constructor
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final ImageView eventImage;
        public final TextView eventTitle;
        public final TextView eventDate;
        public final TextView eventCategory;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            eventCategory = (TextView) itemView.findViewById(R.id.event_category);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            cursor.moveToPosition(position);
//
//            int columnIndex = cursor.getColumnIndex(Contract.EventColumn.ID);
//            final int eventId = cursor.getInt(columnIndex);
//
//            listener.onClickListener(Contract.EventColumn.buildEventUri(
//                    Integer.toString(eventId)));

            // testing
            listener.onClickListener(null);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (viewGroup instanceof RecyclerView) {
            int layoutId = R.layout.main_item;
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
            view.setFocusable(true);
            return new MyViewHolder(view);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
//        cursor.moveToPosition(i);

        String category = context.getString(R.string.sample_category);

        viewHolder.eventCategory.setText(category.toUpperCase());
        viewHolder.eventTitle.setText(data.get(i).toUpperCase());
        viewHolder.eventDate.setText(R.string.sample_date);

        Glide.with(viewHolder.eventImage.getContext())
                .load("https://pbs.twimg.com/profile_images/462501444977836032/G6h25qnI.jpeg")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.eventImage);
        viewHolder.eventImage.setContentDescription(data.get(i));

//        String category = cursor.getString(Event.CATEGORY);
//
//        viewHolder.eventCategory.setText(category.toUpperCase());
//        viewHolder.eventTitle.setText(cursor.getString(Event.TITLE).toUpperCase());
//        viewHolder.eventDate.setText(cursor.getString(Event.START_DATE).toUpperCase());
//
//        Glide.with(viewHolder.eventImage.getContext())
//                .load(cursor.getString(Event.IMAGE))
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewHolder.eventImage);
//        viewHolder.eventImage.setContentDescription(cursor.getString(Event.TITLE));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void swapCursor(Cursor c){
        cursor = c;
        notifyDataSetChanged();
    }
}
