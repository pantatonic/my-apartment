package my.apartment.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonString;


import my.apartment.common.CommonAppUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
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
public class RoomController {
    
    /**
     * 
     * @param buildingId
     * @return 
     */
    @RequestMapping(value = "/room.html", method = {RequestMethod.GET})
    public ModelAndView roomIndex(
            @RequestParam(value = "building_id", required = false) String buildingId
    ) {
        ModelAndView modelAndView = new ModelAndView("room/room_index/room_index");
        
        JSONObject resultGetBuilding = this.getBuilding();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get("data").toString());
        
        JSONObject resultGetRoomStatus = this.getRoomStatus();
        JSONArray jsonArrayRoomStatus = new JSONArray(resultGetRoomStatus.get("data").toString());
        /*for(Integer i = 0; i < ja.length(); i ++) {
            System.out.println(ja.getJSONObject(i));
        }*/

        modelAndView.addObject("buildingList", jsonArrayBuilding);
        modelAndView.addObject("buildingIdString", CommonAppUtils.nullToStringEmpty(buildingId));
        modelAndView.addObject("roomStatusList", jsonArrayRoomStatus);
        modelAndView.addObject("currentDateString", CommonAppUtils.getCurrentDateString());
            
        return modelAndView;
    }
    
    /**
     * 
     * @param buildingId
     * @param response
     * @return 
     */
    @RequestMapping(value = "/room_get_by_building_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String roomGetByBuildingId(
            @RequestParam(value = "building_id", required = true) String buildingId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room/room_get_by_building_id/" + buildingId, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);

            /*if(CommonUtils.countJsonArrayDataFromWS(jsonObjectReturn) == 0) {
                jsonObjectReturn = JsonObjectUtils.setDataNotFound(jsonObjectReturn);
            }*/
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param id
     * @param response
     * @return 
     */
    @RequestMapping(value = "/room_get_by_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String roomGetById(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room/room_get_by_id/" + id, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
            
            if(CommonAppUtils.countJsonArrayDataFromWS(jsonObjectReturn) == 0) {
                jsonObjectReturn = JsonObjectUtils.setDataNotFound(jsonObjectReturn);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param formData
     * @return 
     */
    @RequestMapping(value = "/room_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomSave(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            String[] keyToCleanValue = {
                "price_per_month"
            };
            
            
            /** validate required field */
            JSONObject resultValidateRequired = this.validateRequiredRoomSave(formData);
            if(resultValidateRequired.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultValidateRequired.getString(CommonString.MESSAGE_STRING));
                
                return jsonObjectReturn.toString();
            }
            
            /** validate number field */
            JSONObject resultValidateNumber = this.validateNumberRoomSave(formData);
            if(resultValidateNumber.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultValidateNumber.getString(CommonString.MESSAGE_STRING));
                
                return jsonObjectReturn.toString();
            }
            
            /** validate room_no duplicated */
            JSONObject resultCheckRoomNoDuplicated = this.checkRoomNoDuplicated(formData);
            if(resultCheckRoomNoDuplicated.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultCheckRoomNoDuplicated.getString(CommonString.MESSAGE_STRING));
                
                return jsonObjectReturn.toString();
            }
            
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonAppUtils.simpleConvertFormDataToJSONObject(formData,keyToCleanValue).toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/room_save", entity, String.class, CommonString.UTF8_STRING);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);
            
            jsonObjectReturn = resultWsJsonObject;            
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/room_delete_by_id.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomDeleteById(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("room_id", id);
            
            RestTemplate restTemplate = new RestTemplate();
             
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/room_delete_by_id", parametersMap, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param formData
     * @return 
     */
    private JSONObject validateRequiredRoomSave(MultiValueMap<String, String> formData) {
        String[] keyToValidate = {
            "room_no", "floor_seq", "price_per_month", "room_status_id"
        };
        
        JSONObject result = CommonAppUtils.simpleValidateRequire(formData, keyToValidate);
        
        return result;
    }
    
    /**
     * 
     * @param formData
     * @return 
     */
    private JSONObject validateNumberRoomSave(MultiValueMap<String, String> formData) {
        String[] keyToValidate = {
            "price_per_month"
        };
        
        JSONObject result = CommonAppUtils.simpleValidateNumberWithComma(formData, keyToValidate);
        
        return result;
    }
    
    /**
     * 
     * @param formData
     * @return 
     */
    private JSONObject checkRoomNoDuplicated(MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonAppUtils.simpleConvertFormDataToJSONObject(formData).toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/check_room_no_duplicated", entity, String.class, CommonString.UTF8_STRING);
            JSONObject jsonObjectesult = new JSONObject(resultWs);
            
            /** if it equal "error" */
            if(jsonObjectesult.getString(CommonString.RESULT_STRING).equals(CommonString.ERROR_STRING)) {
                jsonObjectReturn.put(CommonString.RESULT_VALIDATE_STRING, Boolean.FALSE)
                    .put(CommonString.MESSAGE_STRING, CommonString.DATA_DUPLICATED_STRING);
            }
            else {
                jsonObjectReturn.put(CommonString.RESULT_VALIDATE_STRING, Boolean.TRUE)
                        .put(CommonString.MESSAGE_STRING, "");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return jsonObjectReturn;
    }
    
    
    private JSONObject getRoomStatus() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room_status/get_all", String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn;
    }
    
    private JSONObject getBuilding() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "building/building_get", String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn;
    }
    
    @RequestMapping(value = "/room/get_room_manage.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getRoomManage(
            @RequestParam(value = "room_id", required = true) String roomId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);

        try {
            RestTemplate restTemplate = new RestTemplate();

            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "room/get_room_manage/" + roomId, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/room_reservation_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomReservationSave(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonAppUtils.simpleConvertFormDataToJSONObject(formData).toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/room_reservation_save", entity, String.class, CommonString.UTF8_STRING);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);

            jsonObjectReturn = resultWsJsonObject;            
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
}
