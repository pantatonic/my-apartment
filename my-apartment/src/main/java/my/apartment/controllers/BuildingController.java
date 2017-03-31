package my.apartment.controllers;

import java.nio.charset.Charset;
import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonString;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuildingController {

    @Autowired
    MessageSource messageSource;
    
    @RequestMapping(value = "/building.html", method = {RequestMethod.GET})
    public ModelAndView buildingIndex() {
        ModelAndView modelAndView = new ModelAndView("building/building_index/building_index");

        return modelAndView;
    }
    
    @RequestMapping(value = "/building_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String buildingSave(@RequestBody MultiValueMap<String,String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            String[] keyToCleanValue = {
                "electricity_charge_per_unit", "min_electricity_charge", 
                "water_charge_per_unit", "min_water_charge"
            };
            
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonAppUtils.simpleConvertFormDataToJSONObject(formData,keyToCleanValue).toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);

            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "building/building_save", entity, String.class, CommonString.UTF8_STRING);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);
            
            jsonObjectReturn = resultWsJsonObject;
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/building_get.html", method = {RequestMethod.GET})
    @ResponseBody
    public String buildingGet(HttpServletResponse response) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "building/building_get", String.class);

            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/building_get_by_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String buildingGetById(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "building/building_get_by_id/" + id, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
            
            if(CommonAppUtils.countJsonArrayDataFromWS(jsonObjectReturn) == 0) {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.DATA_NOT_FOUND_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/building_delete_by_id.html", method = {RequestMethod.POST})
    @ResponseBody
    public String buildingDeleteById(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("building_id", id);
            
            RestTemplate restTemplate = new RestTemplate();
             
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "building/building_delete_by_id", parametersMap, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
             
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }

}
