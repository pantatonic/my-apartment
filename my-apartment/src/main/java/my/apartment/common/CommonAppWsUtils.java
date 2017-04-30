package my.apartment.common;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
        
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + webservicePath, entity, String.class, CommonString.UTF8_STRING);

        return new JSONObject(resultWs);
    }

}
