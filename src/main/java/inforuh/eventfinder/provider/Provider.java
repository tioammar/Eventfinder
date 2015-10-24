/*
 * Copyright 2015 Aditya Amirullah. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package inforuh.eventfinder.provider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by tioammar
 * on 8/11/15.
 */
public class Provider extends ContentProvider {

    private static final UriMatcher uriMatcher = getUriMatcher();
    private DBHelper dbHelper;

    static final int EVENT = 500;
    static final int EVENT_ID = 501;

    private static UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.PATH_EVENT, EVENT);
        matcher.addURI(authority, Contract.PATH_EVENT + "/*", EVENT_ID);

        return matcher;
    }

    private static final String eventWithId = Contract.EventColumn.ID + " = ? ";

    @Override
    public String getType(Uri uri) {
        final int code = uriMatcher.match(uri);

        switch (code){
            case EVENT:
                return Contract.EventColumn.CONTENT_TYPE;
            case EVENT_ID:
                return Contract.EventColumn.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int code = uriMatcher.match(uri);
        Cursor cursor;
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (code){
            case EVENT: {
                cursor = db.query(
                        Contract.EventColumn.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                break;
            }
            case EVENT_ID: {
                String id = uri.getLastPathSegment();
                cursor = db.query(
                        Contract.EventColumn.TABLE_NAME,
                        projection,
                        eventWithId,
                        new String[]{id},
                        null,
                        null,
                        null
                );
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int code = uriMatcher.match(uri);
        Uri retUri;
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (code){
            case EVENT: {
                db.insertWithOnConflict(Contract.EventColumn.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                retUri = Contract.EventColumn.buildEventUri(values.getAsString(Contract.EventColumn.ID));
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int code = uriMatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        if (selection == null) selection = "1";

        switch (code){
            case EVENT: {
                rowsDeleted = db.delete(Contract.EventColumn.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int code = uriMatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated;

        switch (code){
            case EVENT: {
                rowsUpdated = db.update(Contract.EventColumn.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }

        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final int code = uriMatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int retCount = 0;

        switch (code){
            case EVENT: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values){
                        db.insertWithOnConflict(Contract.EventColumn.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        retCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            }
            default:
                return super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retCount;
    }

    @Override
    @TargetApi(11)
    public void shutdown(){
        dbHelper.close();
        super.shutdown();
    }
}
