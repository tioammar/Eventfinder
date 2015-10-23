package inforuh.eventfinder.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inforuh.eventfinder.R;
import inforuh.eventfinder.io.Event;
import inforuh.eventfinder.provider.Contract;
import inforuh.eventfinder.ui.DetailActivity;

/**
 * Created by tioammar
 * on 8/11/15.
 */
public class MusicFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        EventAdapter.Listener {

    private EventAdapter musicAdapter;

    public MusicFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        musicAdapter = new EventAdapter(getActivity(), this);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.event_list);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(musicAdapter);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = Contract.EventColumn.CATEGORY + " = ? ";
        String selectionArgs = "Music";
        String sortOrder = Contract.EventColumn.START_DATE + " DESC";

        return new CursorLoader(getActivity(),
                Contract.EventColumn.CONTENT_URI,
                Event.PROJECTION,
                selection,
                new String[]{selectionArgs},
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) return;
        musicAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onClickListener(Uri uri) {
        startActivity(new Intent(getActivity(), DetailActivity.class));
    }
}
