package ir.maktab.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date dateUtils(String date) throws ParseException {
        return new SimpleDateFormat("yyyy/mm/dd").parse(date);
    }
}
