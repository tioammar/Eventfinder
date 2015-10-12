package inforuh.eventfinder.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import inforuh.eventfinder.provider.Contract.*;

/**
 * Created by tioammar
 * on 8/11/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "eventfinder";
    static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_EVENT_TABLE = "";

        db.execSQL(SQL_CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventColumn.TABLE_NAME);
        onCreate(db);
    }
}
