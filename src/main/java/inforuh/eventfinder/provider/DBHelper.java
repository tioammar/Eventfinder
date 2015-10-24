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
        final String SQL_CREATE_EVENT_TABLE = "CREATE TABLE " + Contract.EventColumn.TABLE_NAME + " (" +
                Contract.EventColumn.ID + " INTEGER PRIMARY KEY, " +
                Contract.EventColumn.TITLE + " TEXT NOT NULL, " +
                Contract.EventColumn.CONTENT + " TEXT NOT NULL, " +
                Contract.EventColumn.START_DATE + " DATE NOT NULL, " +
                Contract.EventColumn.END_DATE + " DATE NOT NULL, " +
                Contract.EventColumn.CATEGORY + " TEXT NOT NULL, " +
                Contract.EventColumn.IMAGE + " TEXT NOT NULL, " +
                Contract.EventColumn.PRICE + " TEXT NOT NULL, " +
                Contract.EventColumn.LONGITUDE + " REAL NOT NULL, " +
                Contract.EventColumn.LATITUDE + " REAL NOT NULL, " +
//                Contract.EventColumn.CONTACT_NAME + " TEXT NOT NULL, " +
//                Contract.EventColumn.CONTACT_ADDRESS + " TEXT NOT NULL, " +
                Contract.EventColumn.CONTACT_TWITTER + " TEXT NOT NULL, " +
                Contract.EventColumn.CONTACT_FACEBOOK + " TEXT NOT NULL, " +
                Contract.EventColumn.CONTACT_LINE + " TEXT NOT NULL, " +
                Contract.EventColumn.CONTACT_INSTAGRAM + " TEXT NOT NULL, " +
                Contract.EventColumn.CONTACT_PATH + " TEXT NOT NULL, " +
                Contract.EventColumn.BARCODE + " TEXT NOT NULL, " +
                Contract.EventColumn.URL + " TEXT NOT NULL " + " );";

        db.execSQL(SQL_CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventColumn.TABLE_NAME);
        onCreate(db);
    }
}
