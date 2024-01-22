package com.fhtw.taskmanagerclient.helpers;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
/**

 DateTimeHelper is a helper class that contains methods that are used
 to get the start and end dates of the current week, month, and also a
 method to format a LocalDate object to a string.
 */
public class DateTimeHelper {
    /**
     * A SimpleDateFormat object that is used to format dates to the "yyyy-MM-dd" format.
     */
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Returns the start date of the current week.
     * @return A string representation of the start date of the current week in the format "yyyy-MM-dd".
     */
    public static String getWeekStartDate() {

        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        return dateFormat.format(calendar.getTime()).toString();
    }
    /**
     * Returns the end date of the current week.
     * @return A string representation of the start date of the current week in the format "yyyy-MM-dd".
     */
    public static String getWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }

            calendar.add(Calendar.DATE, -1);

        return dateFormat.format(calendar.getTime()).toString();
    }
    /**
     * Returns the start date of the current month.
     * @return A string representation of the start date of the current week in the format "yyyy-MM-dd".
     */
    public static String getMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime()).toString();
    }
    /**
     * Returns the end date of the current month.
     * @return A string representation of the start date of the current week in the format "yyyy-MM-dd".
     */
    public static String getMonthEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime()).toString();
    }
    /**
     * Formats a LocalDate object to a string in the format "yyyy-MM-dd".
     * @param date A LocalDate object that needs to be formatted.
     * @return A string representation of the LocalDate object in the format "yyyy-MM-dd".
     */
    public static String getFormattedDateString(java.time.LocalDate date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return dateFormat.format(Date.from(date.atStartOfDay(defaultZoneId).toInstant())).toString();
    }
}
