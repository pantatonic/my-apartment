package my.apartment.controllers;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.CommonString;
import my.apartment.common.CommonUtils;
import my.apartment.common.JsonObjectUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RoomInvoiceController {
    
    @Autowired  
    private MessageSource messageSource;
    
    /**
     * 
     * @return ModelAndView
     */
    @RequestMapping(value = "/room_invoice.html", method = {RequestMethod.GET})
    public ModelAndView roomInvoice() {
        ModelAndView modelAndView = new ModelAndView("room_invoice/room_invoice_index/room_invoice_index");
        
        JSONObject resultGetBuilding = CommonAppWsUtils.getBuildingList();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get(CommonString.DATA_STRING).toString());
        
        String currentMonth = CommonUtils.getCurrentMonthString();
        String currentYear = CommonUtils.getCurrentYearString();
        
        modelAndView.addObject("buildingList", jsonArrayBuilding);
        modelAndView.addObject("currentYearMonth", currentYear + "-" + currentMonth);
        
        return modelAndView;
    }
    
    /**
     * 
     * @param buildingId
     * @param month
     * @param year
     * @param response
     * @return String
     */
    @RequestMapping(value = "/get_room_invoice_room_detail_list.html")
    @ResponseBody
    public String getRoomInvoiceRoomDetailList(
            @RequestParam(value = "building_id", required = true) String buildingId,
            @RequestParam(value = "month", required = true) String month,
            @RequestParam(value = "year", required = true) String year,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            JSONObject jsonObjectGetCurrentCheckIn = CommonAppWsUtils.get("room/get_current_check_in");
            JSONObject jsonObjectGetRoomInvoiceMonthYear 
                    = CommonAppWsUtils.get("room_invoice/get_room_invoice_month_year/" + buildingId + "/" + month + "/" + year);
            JSONObject jsonObjectGetRoomElectricityWaterMeterByBuildingId 
                    = CommonAppWsUtils.get("electricity_water_meter/get_electricity_water_meter_by_building_id_month_year/" + buildingId + "/" + month + "/" + year);

            jsonObjectReturn.put(CommonString.DATA_STRING, 
                    new JSONObject()
                            .put("currentCheckIn", jsonObjectGetCurrentCheckIn.get(CommonString.DATA_STRING))
                            .put("roomInvoiceRoomDetailList", jsonObjectGetRoomInvoiceMonthYear.get(CommonString.DATA_STRING))
                            .put("roomElectricityWaterMeter", jsonObjectGetRoomElectricityWaterMeterByBuildingId.get(CommonString.DATA_STRING))
            );
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
     * @return String
     */
    @RequestMapping(value = "/create_room_invoice.html", method = {RequestMethod.POST})
    @ResponseBody
    public String createRoomInvoice(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            List<String> roomIdStringList = formData.get("id[]");

            /** validate set of roomId */
            if(roomIdStringList == null) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, "Set of roomId must have data");

                return jsonObjectReturn.toString();
            }
            
            /** validate required field */
            JSONObject resultValidateRequired = CommonAppUtils.simpleValidateRequired(formData, 
                    new String[] {"month", "year"});
            if(resultValidateRequired.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultValidateRequired.getString(CommonString.MESSAGE_STRING));

                return jsonObjectReturn.toString();
            }
            
            
            String requestJson = new JSONObject()
                    .put(CommonString.DATA_STRING, roomIdStringList)
                    .put("month", formData.getFirst("month"))
                    .put("year", formData.getFirst("year"))
                    .toString();

            jsonObjectReturn = CommonAppWsUtils.postWithJsonDataString(requestJson, "room_invoice/create");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
}
