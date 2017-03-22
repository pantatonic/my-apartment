package my.apartment.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class CommonUtils {
    
    public static JSONObject receiveJsonObject(InputStream incomingData) {
        JSONObject jsonObject = new JSONObject();

        try {
            StringBuilder crunchifyBuilder = new StringBuilder();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
            
            jsonObject = new JSONObject(crunchifyBuilder.toString());
        } catch (IOException ex) {
            Logger.getLogger(CommonUtils.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
}
