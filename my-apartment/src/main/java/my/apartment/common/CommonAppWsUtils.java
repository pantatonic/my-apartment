package my.apartment.common;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class CommonAppWsUtils {
    
    public static JSONObject get(String webservicePath) {
        RestTemplate restTemplate = new RestTemplate();
        
        String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + webservicePath, String.class);
        
        return new JSONObject(resultWs);
    }
    
    public static JSONObject postWithJsonDataString(String requestJson, String webservicePath) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = CommonAppUtils.jsonMediaType();
        headers.setContentType(mediaType);
        
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + webservicePath, entity, String.class, CommonString.UTF8_STRING);

        return new JSONObject(resultWs);
    }
    
    public static JSONObject postWithMultiValueMap(MultiValueMap<String, String> parametersMap, String webservicePath) {
        RestTemplate restTemplate = new RestTemplate();

        String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + webservicePath, parametersMap, String.class, CommonString.UTF8_STRING);

        return new JSONObject(resultWs);
    }
    
    public static JSONObject getBuildingList() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            jsonObjectReturn = CommonAppWsUtils.get("building/building_get");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn;
    }

}
