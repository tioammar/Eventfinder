package inforuh.eventfinder.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import inforuh.eventfinder.Config;
import inforuh.eventfinder.R;
import inforuh.eventfinder.io.Event;
import inforuh.eventfinder.io.EventsData;
import inforuh.eventfinder.provider.Contract;

/**
 * Created by tioammar
 * on 8/13/15.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter implements Callback {

    private static final int SYNC_INTERVAL = 60 * 180;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final String LOG_TAG = SyncAdapter.class.getSimpleName();

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                    ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Getting event data...");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Config.URL)
                .build();

        client.newCall(request).enqueue(this);
    }

    public static void periodicSync(Context context, int interval, int flexTime){
        Account account = getAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(interval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, Bundle.EMPTY, interval);
        }
    }

    public static void startSync(Context context){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getAccount(Context context){
        AccountManager accountManager = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);

        Account account = new Account(context.getString(R.string.app_name),
                context.getString(R.string.account_type));

        if (accountManager.getPassword(account) == null){
            accountManager.addAccountExplicitly(account, "", null);
            onAccountCreated(account, context);
        }
        return account;
    }

    private static void onAccountCreated(Account acc, Context context){
        Log.d(LOG_TAG, "Sync account created");
        SyncAdapter.periodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(acc, context.getString(R.string.content_authority), true);
        startSync(context);
    }

    public static void initialize(Context context){
        getAccount(context);
    }

    @Override
    public void onFailure(Request request, IOException e) {
//        startSync(getContext());
        Log.v(LOG_TAG, e.getMessage());
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Request failed");
        handleResponse(response.body().string());
    }

    private void handleResponse(String json) {
        Gson gson = new Gson();
        EventsData response = gson.fromJson(json, EventsData.class);
        List<Event> events = response.events;

        final int size = events.size();
        if (size == 0) return; // empty response

        int matcher = 0;

        Vector<ContentValues> evVector = new Vector<>(size);
        for (Event event : events){
            ContentValues ev = new ContentValues();
            ev.put(Contract.EventColumn.ID, event.id);
            ev.put(Contract.EventColumn.TITLE, event.title);
            ev.put(Contract.EventColumn.CONTENT, event.content);
            ev.put(Contract.EventColumn.CATEGORY, event.category);
            ev.put(Contract.EventColumn.START_DATE, event.dateStart);
            ev.put(Contract.EventColumn.END_DATE, event.endDate);
            ev.put(Contract.EventColumn.LONGITUDE, event.longitude);
            ev.put(Contract.EventColumn.LATITUDE, event.latitude);
            ev.put(Contract.EventColumn.IMAGE, event.picture);
            ev.put(Contract.EventColumn.BARCODE, event.barcode);
            ev.put(Contract.EventColumn.PRICE, event.price);
            ev.put(Contract.EventColumn.URL, event.url);
            ev.put(Contract.EventColumn.CONTACT_TWITTER, event.twitter);
            ev.put(Contract.EventColumn.CONTACT_FACEBOOK, event.facebook);
            ev.put(Contract.EventColumn.CONTACT_LINE, event.line);
            ev.put(Contract.EventColumn.CONTACT_INSTAGRAM, event.instagram);
            ev.put(Contract.EventColumn.CONTACT_PATH, event.path);
            if (evVector.add(ev)) matcher++;
        }

        if (matcher != size) return;
        if (evVector.size() > 0){
            ContentValues[] evArray = new ContentValues[evVector.size()];
            evVector.toArray(evArray);

            long count = getContext().getContentResolver().bulkInsert(Contract.EventColumn.CONTENT_URI,
                    evArray);
            Log.d(LOG_TAG, "Inserted: " + count);
        }
    }
}
