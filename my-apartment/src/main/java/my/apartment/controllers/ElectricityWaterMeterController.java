package my.apartment.controllers;

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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ElectricityWaterMeterController {
    
    @Autowired  
    private MessageSource messageSource;
    
    @RequestMapping(value = "/electricity_water_meter.html", method = {RequestMethod.GET})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("electricity_water_meter/index/index");
        
        JSONObject resultGetBuilding = this.getBuilding();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get(CommonString.DATA_STRING).toString());
        
        modelAndView.addObject("buildingList", jsonArrayBuilding);
        
        return modelAndView;
    }
    
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
    
}
