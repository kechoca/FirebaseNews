package com.tntadvance.firebasenews.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by thana on 9/9/16 AD.
 */
public class DateUtil {
    Calendar calendar;

    public DateUtil() {
//        calendar = Calendar.getInstance();
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getDate() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String dateThai(String strDate) {
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year = 0, month = 0, day = 0;
        try {
            Calendar c = Calendar.getInstance();
            Date date = df.parse(strDate);
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return String.format("%s %s %s", day, Months[month], year + 543);
    }

    public static String dateThaiFull(String strDate) {
        String Months[] = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฟศจิกายน", "ธันวาคม"};

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        int year = 0, month = 0, day = 0;
        try {
            Calendar c = Calendar.getInstance();
            Date date = df.parse(strDate);
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return String.format("%s %s", Months[month], year);
    }

    public static String dateThaiNow() {
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year = 0, month = 0, day = 0;

        Calendar c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);


        return String.format("%s %s %s", day, Months[month], year + 543);
    }

    public static String timeStampToDateString(String dateString){

        long timeStamp = Long.parseLong(dateString) * 1000L;

        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception e){
            return e.toString();
        }
    }

    public static String currentTimeStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }


}

