package my.apartment.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;


public class CommonWsUtils {
    
    public static final String MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON  + ";charset=utf-8";
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    
    public static JSONObject receiveJsonObject(InputStream incomingData) {
        JSONObject jsonObject = new JSONObject();

        try {
            StringBuilder crunchifyBuilder = new StringBuilder();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData, "UTF-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
            
            jsonObject = new JSONObject(crunchifyBuilder.toString());
        } catch (IOException ex) {
            Logger.getLogger(CommonWsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jsonObject;
    }
    
    public static Integer stringToInteger(String integerString) {
        if(integerString.isEmpty()) {
            return null;
        }
        else {
            return Integer.parseInt(integerString, 10);
        }
    }
    
    public static BigDecimal stringToBigDecimal(String bigDecimalString) {
        if(bigDecimalString.isEmpty()) {
            return null;
        }
        else {
            return new BigDecimal(bigDecimalString);
        }
    }
    
    public static Integer integerZeroToNull(int integerValue) {
        return integerValue == 0 ? null : integerValue;
    }
    
    public static Date stringToDate(String dateString) throws ParseException {
        if(dateString.isEmpty()) {
            return null;
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
            
            return simpleDateFormat.parse(dateString);
        }
    }
    
    public static String strToString(String str) {
        if(str.isEmpty()) {
            return null;
        }
        else {
            return str;
        }
    }
    

}
