package my.apartment.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
public class ElectricityWaterMeterController {
    
    @Autowired  
    private MessageSource messageSource;
    
    /**
     * 
     * @return ModelAndView
     */
    @RequestMapping(value = "/electricity_water_meter.html", method = {RequestMethod.GET})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("electricity_water_meter/index/index");
        
        JSONObject resultGetBuilding = CommonAppWsUtils.getBuildingList();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get(CommonString.DATA_STRING).toString());
        
        String currentMonth = CommonUtils.getCurrentMonthString();
        String currentYear = CommonUtils.getCurrentYearString();
        
        modelAndView.addObject("buildingList", jsonArrayBuilding);
        modelAndView.addObject("currentYearMonth", currentYear + "-" + currentMonth);
        
        
        Integer allowPresentMonth = Integer.parseInt(CommonUtils.getCurrentMonthString(), 10);
        Integer allowPresentYear = Integer.parseInt(CommonUtils.getCurrentYearString(), 10);
        
        modelAndView.addObject("allowPresentYear", allowPresentYear);
        modelAndView.addObject("allowPresentMonth", allowPresentMonth);
        
        
        HashMap<String, Integer> hashMapPreviousMonthYear = CommonUtils.getPreviousMonthYear(allowPresentMonth, allowPresentYear);
        modelAndView.addObject("allowPreviousYear", hashMapPreviousMonthYear.get("year"));
        modelAndView.addObject("allowPreviousMonth", hashMapPreviousMonthYear.get("month"));
        
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
    @RequestMapping(value = "/get_room_electric_water_meter_by_building_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getRoomElectricWaterMeterByBuildingId(
            @RequestParam(value = "building_id", required = true) String buildingId,
            @RequestParam(value = "month", required = true) String month,
            @RequestParam(value = "year", required = true) String year,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);

        try {
            jsonObjectReturn = CommonAppWsUtils.get("room/get_room_electric_water_meter_by_building_id/" + buildingId + "/" + year + "/" + month);
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
    @RequestMapping(value = "/electricity_water_meter_save.html", method = {RequestMethod.POST})
    @ResponseBody
    public String electricityWaterMeterSave(@RequestBody MultiValueMap<String, String> formData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            List<String> roomIdList = formData.get("id");
        
            List<String> previousElectricList = formData.get("previous_electric");
            List<String> presentElectricMeterList = formData.get("present_electric_meter");

            List<String> previousWater = formData.get("previous_water");
            List<String> presentWaterMeterList = formData.get("present_water_meter");

            String month = formData.getFirst("month");
            String year = formData.getFirst("year");

            Boolean validateEmpty = Boolean.TRUE;
            Boolean validateNumber = Boolean.TRUE;

            JSONArray jsonArray = new JSONArray();
            for(Integer i = 0; i < roomIdList.size(); i++) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", roomIdList.get(i));
                jsonObject.put("previous_electric", previousElectricList.get(i));
                jsonObject.put("present_electric_meter", presentElectricMeterList.get(i));
                jsonObject.put("previous_water", previousWater.get(i));
                jsonObject.put("present_water_meter", presentWaterMeterList.get(i));

                /** validate mandatory of each data */
                if(roomIdList.get(i).isEmpty() 
                        || previousElectricList.get(i).isEmpty() || presentElectricMeterList.get(i).isEmpty() 
                        || previousWater.get(i).isEmpty() || presentWaterMeterList.get(i).isEmpty()) {
                    validateEmpty = Boolean.FALSE;
                }

                /** validate meter is number of each data */
                if(!CommonAppUtils.isNumber(roomIdList.get(i)) 
                        || !CommonAppUtils.isNumber(previousElectricList.get(i)) || !CommonAppUtils.isNumber(presentElectricMeterList.get(i)) 
                        || !CommonAppUtils.isNumber(previousWater.get(i)) || !CommonAppUtils.isNumber(presentWaterMeterList.get(i))) {
                    validateNumber = Boolean.FALSE;
                }

                jsonArray.put(jsonObject);
            }

            if(Objects.equals(validateEmpty, Boolean.FALSE)) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        "Mandatory data is : id, previous_electric, present_electric_meter, previous_water, present_water_meter");

                return jsonObjectReturn.toString();
            }

            if(Objects.equals(validateNumber, Boolean.FALSE)) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        "Data must number only : id, previous_electric, present_electric_meter, previous_water, present_water_meter");

                return jsonObjectReturn.toString();
            }

            String requestJson = new JSONObject()
                    .put(CommonString.DATA_STRING, jsonArray)
                    .put("month", month)
                    .put("year", year)
                    .toString();

            jsonObjectReturn = CommonAppWsUtils.postWithJsonDataString(requestJson, "electricity_water_meter/save");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
}
