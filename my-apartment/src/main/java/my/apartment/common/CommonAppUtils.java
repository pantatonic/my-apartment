package my.apartment.common;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

public class CommonAppUtils {

    public static String getFormData(String key, MultiValueMap<String, String> formData) {
        return formData.get(key).get(0);
    }

    public static JSONObject simpleConvertFormDataToJSONObject(MultiValueMap<String, String> formData) {
        JSONObject jsonObject = new JSONObject();

        Iterator<String> iterator = formData.keySet().iterator();

        while (iterator.hasNext()) {
            String keyString = (String) iterator.next();
            String value = formData.getFirst(keyString).trim();

            jsonObject.put(keyString, value);
        }

        return jsonObject;
    }
    
    public static JSONObject simpleConvertFormDataToJSONObject(MultiValueMap<String, String> formData, String[] keyToCleanValue) {
        JSONObject jsonObject = new JSONObject();

        Iterator<String> iterator = formData.keySet().iterator();
        
        while (iterator.hasNext()) {
            String keyString = (String) iterator.next();
            String value = formData.getFirst(keyString).trim();
            
            if(ArrayUtils.contains(keyToCleanValue, keyString)) {
                value = StringUtils.remove(value, ",").trim();
            }

            jsonObject.put(keyString, value);
        }

        return jsonObject;
    }
    
    public static MediaType jsonMediaType() {
        return new MediaType("application", "json", Charset.forName("UTF-8"));
    }
    
    public static void setResponseHeader(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
    }
    
    public static String nullToStringEmpty(String dataString) {
        return dataString == null ? StringUtils.EMPTY : dataString;
    }
    
    public static JSONArray getJsonArrayDataFromWS(JSONObject resultWsJsonObject) {
        return new JSONArray(resultWsJsonObject.get("data").toString());
    }
    
    public static int countJsonArrayDataFromWS(JSONObject resultWsJsonObject) {
        return CommonAppUtils.getJsonArrayDataFromWS(resultWsJsonObject).length();
    }
    
    public static String getCurrentDateString() {
        Date date = new Date();

        return new SimpleDateFormat(CommonString.DATE_FORMAT_STRING).format(date);
    }
    
    public static JSONObject simpleValidateRequired(
            MultiValueMap<String, String> formData,
            String[] keyToValidate
    ) {
        JSONObject jsonObject = new JSONObject();
        
        Iterator<String> iterator = formData.keySet().iterator();
        
        Boolean validatePass = Boolean.TRUE;
        
        ArrayList<String> requiredField = new ArrayList<String>();

        while (iterator.hasNext()) {
            String keyString = (String) iterator.next();
            String value = formData.getFirst(keyString);
            
            if(ArrayUtils.contains(keyToValidate, keyString)) {
                if(value.equalsIgnoreCase("")) {
                    validatePass = Boolean.FALSE;
                    
                    requiredField.add(keyString);
                }
            }
        }
         
        jsonObject.put(CommonString.RESULT_VALIDATE_STRING, validatePass)
                    .put(CommonString.MESSAGE_STRING, CommonString.REQUIRED_FIELD_STRING + " : " + requiredField.toString());
        
        return jsonObject;
    }
    
    public static JSONObject simpleValidateNumberWithComma(
            MultiValueMap<String, String> formData,
            String[] keyToValidate
    ) {
        JSONObject jsonObject = new JSONObject();
        
        Iterator<String> iterator = formData.keySet().iterator();
        
        Boolean validatePass = Boolean.TRUE;
        
        ArrayList<String> validateFailField = new ArrayList<String>();
        
        Integer tryInteger = null;
        Boolean resultTryInteger = Boolean.TRUE;
        
        BigDecimal tryBigDecimal = null;
        Boolean resultTryBigDecimal = Boolean.TRUE;
        
        while (iterator.hasNext()) {
            String keyString = (String) iterator.next();
            String value = formData.getFirst(keyString);
            
            if(ArrayUtils.contains(keyToValidate, keyString)) {
                value = StringUtils.remove(value, ",");
                
                try {
                    tryInteger = Integer.parseInt(value, 10);
                }
                catch(Exception e) {
                    resultTryInteger = Boolean.FALSE;
                }

                try {
                    tryBigDecimal = new BigDecimal(value);
                }
                catch(Exception e) {
                    resultTryBigDecimal = Boolean.FALSE;
                }
                
                if(Objects.equals(resultTryInteger, Boolean.FALSE) 
                    && Objects.equals(resultTryBigDecimal, Boolean.FALSE)) {
                    
                    validateFailField.add(keyString);
                    validatePass = Boolean.FALSE;
                }
            }

        }
        
        jsonObject.put(CommonString.RESULT_VALIDATE_STRING, validatePass)
                    .put(CommonString.MESSAGE_STRING, CommonString.FIELD_MUST_NUMBER_STRIN + " : " + validateFailField.toString());
        
        
        return jsonObject;
    }
    
    public static Boolean isNumber(String stringNumber) {
        Boolean result = Boolean.TRUE;
        
        try {
            Long.parseLong(stringNumber, 10);
        }
        catch(Exception e) {
            result = Boolean.FALSE;
        }
        
        return result;
    }
    
    public static String getStringJsonObject(String key, JSONObject jsonObject) {
        if(jsonObject.has(key)) {
            return jsonObject.getString(key);
        }
        else {
            return null;
        }
    }

}
