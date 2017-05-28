package my.apartment.controllers;

import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.CommonString;
import my.apartment.common.JsonObjectUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
     * @return 
     */
    @RequestMapping(value = "/electricity_water_meter.html", method = {RequestMethod.GET})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("electricity_water_meter/index/index");
        
        JSONObject resultGetBuilding = this.getBuilding();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get(CommonString.DATA_STRING).toString());
        
        modelAndView.addObject("buildingList", jsonArrayBuilding);
        
        return modelAndView;
    }
    
    /**
     * 
     * @return 
     */
    private JSONObject getBuilding() {
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
    
    /**
     * 
     * @param buildingId
     * @param response
     * @return 
     */
    @RequestMapping(value = "/get_room_electric_water_meter_by_building_id.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getRoomElectricWaterMeterByBuildingId(
            @RequestParam(value = "building_id", required = true) String buildingId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        System.out.println("buildingId : " + buildingId);
        try {
            jsonObjectReturn = CommonAppWsUtils.get("room/get_room_electric_water_meter_by_building_id/" + buildingId);
            
            System.out.println("---- C ----");
            System.out.println(jsonObjectReturn);
            System.out.println("---- C ----");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
}
