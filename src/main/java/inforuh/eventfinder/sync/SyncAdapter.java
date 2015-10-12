package inforuh.eventfinder.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by tioammar
 * on 8/13/15.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        // TODO
    }

    public static void periodicSync(Context context, int interval, int flextime){
        // TODO
    }

    public static void startSync(Context context){
        // TODO
    }

    public static void getAccout(Context context){
        // TODO
    }

    private static void onAccountCreated(Account acc, Context context){
        // TODO
    }

    public static void initialize(Context context){
        getAccout(context);
    }
}
