package my.apartment.common;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CommonUtils {
    
    public static String getCurrentYearString() {
        Date date = new Date();
        
        return new SimpleDateFormat(CommonString.YEAR_FORMAT_STRING).format(date);
    }
    
    public static String getCurrentMonthString() {
        Date date = new Date();
        
        return new SimpleDateFormat(CommonString.MONTH_FORMAT_STRING).format(date);
    }
    
    public static String getCurrentDayString() {
        Date date = new Date();
        
        return new SimpleDateFormat(CommonString.DAY_FORMAT_STRING).format(date);
    }
    
    public static String getZeroFillWithNumber(Integer numberInteger, Integer zeroFill) {
        return String.format("%0" + zeroFill.toString() + "d", numberInteger);
    }
    
}
