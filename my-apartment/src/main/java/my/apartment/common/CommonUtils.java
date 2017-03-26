package my.apartment.common;

import java.nio.charset.Charset;
import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

public class CommonUtils {

    public static String getFormData(String key, MultiValueMap<String, String> formData) {
        return formData.get(key).get(0);
    }

    public static JSONObject simpleConvertFormDataToJSONObject(MultiValueMap<String, String> formData) {
        JSONObject jsonObject = new JSONObject();

        Iterator<String> iterator = formData.keySet().iterator();

        while (iterator.hasNext()) {
            String keyString = (String) iterator.next();
            String value = formData.getFirst(keyString);
            System.out.println(keyString + " : " + value);
            jsonObject.put(keyString, value);
        }

        return jsonObject;
    }
    
    public static JSONObject simpleConvertFormDataToJSONObject(MultiValueMap<String, String> formData, String[] keyToCleanValue) {
        JSONObject jsonObject = new JSONObject();

        Iterator<String> iterator = formData.keySet().iterator();
        
        /*for(String k : keyToCleanValue) {
            System.out.println(keyToCleanValue[0]);
        }*/
        
        /*if(ArrayUtils.contains(keyToCleanValue, "electricity_charge_per_unit")) {
            System.out.println("Found");
        }*/

        while (iterator.hasNext()) {
            String keyString = (String) iterator.next();
            String value = formData.getFirst(keyString);
            
            if(ArrayUtils.contains(keyToCleanValue, keyString)) {
                value = StringUtils.remove(value, ",");
            }

            jsonObject.put(keyString, value);
        }
System.out.println(jsonObject);
        return jsonObject;
    }
    
    public static MediaType jsonMediaType() {
        return new MediaType("application", "json", Charset.forName("UTF-8"));
    }
    
    public static void setResponseHeader(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
    }

}
