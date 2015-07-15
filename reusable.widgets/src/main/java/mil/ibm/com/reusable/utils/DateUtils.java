/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private Context ctx;

    public DateUtils(Context ctx) {
        super();
        this.ctx = ctx;
    }

    public String getFormattedTime(float timeInMinutes) {
        // Change minutes to milliseconds
        Locale currentLocale = ctx.getResources().getConfiguration().locale;
        // Date from backend is in UTC
        Date date = new Date((long) (timeInMinutes * 60 * 1000));
        DateFormat formatter = new SimpleDateFormat("H:mm", currentLocale);
        Date localDate = new Date(date.getTime()
                + TimeZone.getDefault().getOffset(date.getTime()));
        return formatter.format(localDate);
    }

    public String getFormattedDate(float timeInMinutes) {
        // Change minutes to milliseconds
        Locale currentLocale = ctx.getResources().getConfiguration().locale;
        Date date = new Date((long) (timeInMinutes * 60 * 1000));
        DateFormat formatter = new SimpleDateFormat("MMM dd", currentLocale);
        Date localDate = new Date(date.getTime()
                + TimeZone.getDefault().getOffset(date.getTime()));
        String dateFormatted = formatter.format(localDate);
        return dateFormatted.toUpperCase(currentLocale);
    }
}
