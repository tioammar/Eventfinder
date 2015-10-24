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

package inforuh.eventfinder;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UnknownFormatConversionException;

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

    public static String formatDate(Context context, int date){
        switch (date){
            case 1: return context.getString(R.string.one);
            case 2: return context.getString(R.string.two);
            case 3: return context.getString(R.string.three);
            case 21: return context.getString(R.string.twenty_one);
            case 22: return context.getString(R.string.twenty_two);
            case 23: return context.getString(R.string.twenty_three);
            case 31: return context.getString(R.string.thirty_one);
            default: return date + "th";
        }
    }

    public static String formatMonth(Context context, int month){
        switch (month){
            case 0: return context.getString(R.string.january);
            case 1: return context.getString(R.string.february);
            case 2: return context.getString(R.string.march);
            case 3: return context.getString(R.string.april);
            case 4: return context.getString(R.string.may);
            case 5: return context.getString(R.string.june);
            case 6: return context.getString(R.string.july);
            case 7: return context.getString(R.string.august);
            case 8: return context.getString(R.string.september);
            case 9: return context.getString(R.string.october);
            case 10: return context.getString(R.string.november);
            case 11: return context.getString(R.string.december);
            default: throw new UnknownFormatConversionException("Unknown month format");
        }
    }
}
