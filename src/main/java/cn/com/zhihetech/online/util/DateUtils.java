package cn.com.zhihetech.online.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ShenYunjie on 2015/12/18.
 */
public class DateUtils {

    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(date);
    }

    public static Date String2Date(String target) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date String2DateTime(String target) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
