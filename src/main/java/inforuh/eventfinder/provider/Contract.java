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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tioammar
 * on 8/11/15.
 */
public class Contract {

    public static final String CONTENT_AUTHORITY = "inforuh.eventfinder";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_EVENT = "event";

    public static final class EventColumn implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;

        public static final String TABLE_NAME = PATH_EVENT;

        // Column structure
        public static final String ID = "event_id";
        public static final String TITLE = "event_title";
        public static final String CONTENT = "event_content";
        public static final String START_DATE = "event_start_date";
        public static final String END_DATE = "event_end_date";
        public static final String CATEGORY = "event_category";
        public static final String IMAGE = "event_image";
        public static final String PRICE = "event_price";
        public static final String LONGITUDE = "event_longitude";
        public static final String LATITUDE = "event_latitude";

        public static final String CONTACT_MAIN = "event_contact_main";
        public static final String CONTACT_TWITTER = "event_contact_twitter";
        public static final String CONTACT_FACEBOOK = "event_contact_facebook";
        public static final String CONTACT_LINE = "event_contact_line";
        public static final String CONTACT_INSTAGRAM = "event_contact_instagram";
        public static final String CONTACT_PATH = "event_contact_path";
        public static final String BARCODE = "event_barcode";
        public static final String URL = "event_url";
        public static final String LOCATION = "event_location";
        public static final String MAP_ZOOM = "event_map_zoom";
        public static final String ORGANIZER = "event_organizer";

        public static Uri buildEventUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}
