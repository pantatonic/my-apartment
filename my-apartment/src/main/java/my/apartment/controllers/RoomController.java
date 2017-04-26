package my.apartment.controllers;

import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonString;


import my.apartment.common.CommonAppUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    
    @Autowired  
    private MessageSource messageSource;
    
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
    
    /**
     * 
     * @param id
     * @param response
     * @return 
     */
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
    
    /**
     * 
     * @return 
     */
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
    
    /**
     * 
     * @return 
     */
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
    
    /**
     * 
     * @param response
     * @return 
     */
    @RequestMapping(value = "/room/get_room_manage_detail_list.html")
    @ResponseBody
    public String getRoomManageDetailList(
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            /** get current reserve */
            RestTemplate restTemplate = new RestTemplate();
            
            String resultWsGetCurrentReserve = restTemplate.getForObject(ServiceDomain.WS_URL + "room/get_current_reserve", String.class);
            JSONObject jsonObjectGetCurrentReserve = new JSONObject(resultWsGetCurrentReserve);
            
            String resultWsGetCurrentCheckIn = restTemplate.getForObject(ServiceDomain.WS_URL + "room/get_current_check_in", String.class);
            JSONObject jsonObjectGetCurrentCheckIn = new JSONObject(resultWsGetCurrentCheckIn);

            jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, "");
            
            jsonObjectReturn.put(CommonString.DATA_STRING, 
                    new JSONObject().put("reserve", jsonObjectGetCurrentReserve.get(CommonString.DATA_STRING))
                            .put("currentCheckIn", jsonObjectGetCurrentCheckIn.get(CommonString.DATA_STRING))
            );

            
            /*JSONArray jsonArray = new JSONArray();
            
            JSONObject jsonObjectReserve = new JSONObject().put("aaa", "AAA").put("bbb", "BBB").put("ccc", "CCC");
            JSONObject jsonObjectStayed = new JSONObject().put("ddd", "DDD").put("eee", "EEE").put("fff", "FFF");
            JSONObject jsonObjectNoticeCheckout = new JSONObject().put("ggg", "GGG").put("hhh", "HHH").put("iii", "III");
            
            jsonArray.put(jsonObjectReserve);
            jsonArray.put(jsonObjectStayed);
            jsonArray.put(jsonObjectNoticeCheckout);
            
            jsonObjectReturn.put("reserve", jsonArray);*/
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param roomId
     * @param response
     * @return 
     */
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
    
    /**
     * 
     * @param formData
     * @return 
     */
    @RequestMapping(value = "/room_reservation_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomReservationSave(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            /** validate required field */
            JSONObject resultValidateRequired = CommonAppUtils.simpleValidateRequire(formData, 
                    new String[] {"id_card", "reserve_name", "reserve_lastname", "status"});
            
            if(resultValidateRequired.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultValidateRequired.getString(CommonString.MESSAGE_STRING));
                
                return jsonObjectReturn.toString();
            }


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
    
    /**
     * 
     * @param formData
     * @return 
     */
    @RequestMapping(value = "/room_check_in_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomCheckInSave(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            /** validate required field */
            JSONObject resultValidateRequired = CommonAppUtils.simpleValidateRequire(formData, 
                    new String[] {"check_in_date", "id_card", "name", "lastname"});
            
            if(resultValidateRequired.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultValidateRequired.getString(CommonString.MESSAGE_STRING));
                
                return jsonObjectReturn.toString();
            }
            
            
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = CommonAppUtils.simpleConvertFormDataToJSONObject(formData).toString();
            
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);

            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/room_check_in_save", entity, String.class, CommonString.UTF8_STRING);

            JSONObject resultWsJsonObject = new JSONObject(resultWs);

            jsonObjectReturn = resultWsJsonObject;            
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param roomId
     * @param draw
     * @param start
     * @param length
     * @param search
     * @param response
     * @return 
     */
    @RequestMapping(value = "/get_reservation_list.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getReservationList(
            @RequestParam(value = "room_id", required = true) String roomId,
            @RequestParam(value = "draw", required = true) String draw,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "length", required = true) String length,
            @RequestParam(value = "search[value]", required = false) String search,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();

        CommonAppUtils.setResponseHeader(response);
        
        try {
            JSONObject requestJsonObject = new JSONObject();
            requestJsonObject.put("room_id", roomId).put("start", start)
                    .put("length", length).put("search", search.trim());
            
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = requestJsonObject.toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/get_reservation_list", entity, String.class, CommonString.UTF8_STRING);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);
            
            JSONArray jsonArrayData = resultWsJsonObject.getJSONArray(CommonString.DATA_STRING);
            JSONArray jsonArrayReturn = new JSONArray();
            
            for(Integer i = 0; i <= jsonArrayData.length() - 1; i++) {
                JSONObject j = jsonArrayData.getJSONObject(i);
                JSONObject tempJsonObject = new JSONObject();
                
                String reserveStatus = this.getReserveStatusString(Integer.parseInt(JsonObjectUtils.getDataStringWithEmpty("status", j), 10));

                tempJsonObject.put("reserveDate", JsonObjectUtils.getDataStringWithEmpty("reserveDate", j));
                tempJsonObject.put("reserveExpired", JsonObjectUtils.getDataStringWithEmpty("reserveExpired", j));
                tempJsonObject.put("idCard", JsonObjectUtils.getDataStringWithEmpty("idCard", j));
                tempJsonObject.put("name", JsonObjectUtils.getDataStringWithEmpty("reserveName", j) 
                        + " " + JsonObjectUtils.getDataStringWithEmpty("reserveLastname", j));
                tempJsonObject.put("status", reserveStatus);
                
                jsonArrayReturn.put(tempJsonObject);
            }

            jsonObjectReturn.put("draw", draw)
                    .put("recordsTotal", resultWsJsonObject.getInt("totalRecords"))
                    .put("recordsFiltered", resultWsJsonObject.getInt("totalRecords"))
                    .put(CommonString.DATA_STRING, jsonArrayReturn);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param status
     * @return 
     */
    private String getReserveStatusString(Integer status) {
        String statusString;
       
        switch(status) {
            case 1: statusString = "<span class=\"label label-warning\">"
                    + messageSource.getMessage("room.reserve",null, LocaleContextHolder.getLocale()) + "</span>";
                break;
            case 2: statusString = "<span class=\"label bg-maroon\">"
                    + messageSource.getMessage("room.close_reserve",null, LocaleContextHolder.getLocale()) + "</span>";
                break;
            case 3: statusString = "<span class=\"label bg-maroon\">"
                    + messageSource.getMessage("room.close_reserve_for_checkin",null, LocaleContextHolder.getLocale()) + "</span>";
                break;
            default: statusString = "";
                break;
        }
        
        return statusString;
    }
    
    /**
     * 
     * @param roomId
     * @param draw
     * @param start
     * @param length
     * @param search
     * @param response
     * @return 
     */
    @RequestMapping(value = "/get_check_in_out_list.html")
    @ResponseBody
    public String getCheckInOutList(
            @RequestParam(value = "room_id", required = true) String roomId,
            @RequestParam(value = "draw", required = true) String draw,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "length", required = true) String length,
            @RequestParam(value = "search[value]", required = false) String search,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();

        CommonAppUtils.setResponseHeader(response);
        
        try {
            JSONObject requestJsonObject = new JSONObject();
            requestJsonObject.put("room_id", roomId).put("start", start)
                    .put("length", length).put("search", search.trim());
            
            RestTemplate restTemplate = new RestTemplate();
            String requestJson = requestJsonObject.toString();
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = CommonAppUtils.jsonMediaType();
            headers.setContentType(mediaType);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/get_check_in_out_list", entity, String.class, CommonString.UTF8_STRING);
            
            JSONObject resultWsJsonObject = new JSONObject(resultWs);
            
            JSONArray jsonArrayData = resultWsJsonObject.getJSONArray(CommonString.DATA_STRING);
            JSONArray jsonArrayReturn = new JSONArray();
            
            for(Integer i = 0; i <= jsonArrayData.length() - 1; i++) {
                JSONObject j = jsonArrayData.getJSONObject(i);
                JSONObject tempJsonObject = new JSONObject();
                
                tempJsonObject.put("checkInDate", JsonObjectUtils.getDataStringWithEmpty("checkInDate", j));
                tempJsonObject.put("checkOutDate", JsonObjectUtils.getDataStringWithEmpty("checkOutDate", j));
                tempJsonObject.put("idCard", JsonObjectUtils.getDataStringWithEmpty("idCard", j));
                tempJsonObject.put("name", JsonObjectUtils.getDataStringWithEmpty("name", j) 
                    + " " + JsonObjectUtils.getDataStringWithEmpty("lastname", j));
                tempJsonObject.put("type", 
                        this.getCheckInOutTypeLabel(JsonObjectUtils.getDataStringWithEmpty("checkOutDate", j)));

                jsonArrayReturn.put(tempJsonObject);
            }
            
            jsonObjectReturn.put("draw", draw)
                .put("recordsTotal", resultWsJsonObject.getInt("totalRecords"))
                .put("recordsFiltered", resultWsJsonObject.getInt("totalRecords"))
                .put(CommonString.DATA_STRING, jsonArrayReturn);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param checkOutDateString
     * @return 
     */
    private String getCheckInOutTypeLabel(String checkOutDateString) {
        if(checkOutDateString.isEmpty()) {
            return "<span class=\"label label-info\">"
                    + messageSource.getMessage("room.check_in", null, LocaleContextHolder.getLocale()) + "</span>";
        }
        else {
            return "<span class=\"label label-primary\">"
                    + messageSource.getMessage("room.check_out", null, LocaleContextHolder.getLocale()) + "</span>";
        }
    }
    
    @RequestMapping(value = "/room_check_out.html", method = {RequestMethod.POST})
    @ResponseBody
    public String roomCheckOut(
            @RequestParam(value = "room_id", required = true) String roomId,
            @RequestParam(value = "number_code", required = true) String numberCode,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("room_id", roomId);
            parametersMap.add("number_code", numberCode);
            
            RestTemplate restTemplate = new RestTemplate();
            
            String resultWs = restTemplate.postForObject(ServiceDomain.WS_URL + "room/room_check_out", parametersMap, String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
}
