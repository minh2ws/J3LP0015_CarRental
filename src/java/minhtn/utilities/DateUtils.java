/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;
import java.sql.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

/**
 *
 * @author minhv
 */
public class DateUtils implements Serializable {

    /**
     * Fix bug from SQL Server 2014 when getting date. It is return date less
     * than 2 days in data in SQL Server
     *
     * @param curDate
     * @return correctDate Date
     */
    public static Date fixDate(Date curDate) {
        LocalDate lcDate = new LocalDate(curDate, DateTimeZone.UTC);
        LocalDate tomorrow = lcDate.plusDays(1);

        DateTime startOfDay = tomorrow.toDateTimeAtStartOfDay(DateTimeZone.UTC);

        return new Date(startOfDay.getMillis());
    }
    
    public static Date getCurrentDate() {
        LocalDate date = new LocalDate(DateTimeZone.UTC).now();
        return new Date(date.toDateTimeAtStartOfDay().getMillis());
    }
    
    /**
     * Calculate today from date to date
     * @param from from Date
     * @param to to Date
     * @return totalDay from Date YYYY-MM-dd to Date YYYY-MM-dd
     */
    public static int calculateDay(Date from, Date to) {
        long days = to.getTime() - from.getTime();
        //minus is less than one day so need to plus one date by 24h * 60m * 60s * 60milis
        return (int) ((days + 86400000)/86400000); //24h * 60m * 60s * 60milis
    }
}
