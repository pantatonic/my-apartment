package my.apartment.common;

import java.util.List;
import org.json.JSONObject;


public class JsonObjectUtils {
    
    public static JSONObject setDataNotFound(JSONObject jsonObject) {
        jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, CommonString.DATA_NOT_FOUND_STRING);
        
        return jsonObject;
    }
    
    public static JSONObject setErrorWithMessage(JSONObject jsonObject, String message) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, message);
    }
    
    public static JSONObject setControllerError(JSONObject jsonObject) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
    }
    
    public static JSONObject setServiceError(JSONObject jsonObject) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
    }
    
    public static JSONObject setSuccessWithMessage(
            JSONObject jsonObject,
            String message
    ) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                .put(CommonString.MESSAGE_STRING, message);
    }
    
    public static JSONObject setSuccessWithMessageDataList(
            JSONObject jsonObject, 
            String message,
            List dataList
    ) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.MESSAGE_STRING, message)
                    .put(CommonString.DATA_STRING, dataList);
    }
    
    public static JSONObject setSuccessWithDataList(
            JSONObject jsonObject,
            List dataList
    ) {
        return jsonObject.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                .put(CommonString.DATA_STRING, dataList);
    }
    
    public static String getStringData(String key, JSONObject jsonObject) {
        if(!jsonObject.has(key)) {
            return null;
        }
        else {
            return jsonObject.getString(key);
        }
    }
    
    public static Integer getIntData(String key, JSONObject jsonObject) {
        if(!jsonObject.has(key)) {
            return null;
        }
        else {
            return jsonObject.getInt(key);
        }
    }
    
    public static Boolean getBooleanData(String key, JSONObject jsonObject) {
        if(!jsonObject.has(key)) {
            return null;
        }
        else {
            return jsonObject.getBoolean(key);
        }
    }
    
    public static String getDataStringWithEmpty(String key, JSONObject jsonObject) {
        if(!jsonObject.has(key)) {
            return "";
        }
        else {
            if(jsonObject.get(key) instanceof String) {
                return jsonObject.getString(key);
            }
            else
            if(jsonObject.get(key) instanceof Integer) {
                return Integer.toString(jsonObject.getInt(key));
            }
            else
            if(jsonObject.get(key) instanceof Boolean) {
                return Boolean.toString(jsonObject.getBoolean(key));
            }
            else {
                return "";
            }
        }
        
    }
    
}
