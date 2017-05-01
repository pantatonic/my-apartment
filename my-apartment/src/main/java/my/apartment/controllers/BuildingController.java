package my.apartment.controllers;

import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonString;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.JsonObjectUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
            
            /** begin validate required field */
            String[] keyToValidate = {
                "name", "electricity_meter_digit", "electricity_charge_per_unit", 
                "water_meter_digit", "water_charge_per_unit"
            };

            JSONObject resultValidateRequired = CommonAppUtils.simpleValidateRequired(formData, keyToValidate);
            if(resultValidateRequired.getBoolean(CommonString.RESULT_VALIDATE_STRING) == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        resultValidateRequired.getString(CommonString.MESSAGE_STRING));
                
                return jsonObjectReturn.toString();
            }
            /** end validate required field */
            
            
            String requestJson = CommonAppUtils.simpleConvertFormDataToJSONObject(formData,keyToCleanValue).toString();

            jsonObjectReturn = CommonAppWsUtils.postWithJsonDataString(requestJson, "building/building_save");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/building_get.html", method = {RequestMethod.GET})
    @ResponseBody
    public String buildingGet(HttpServletResponse response) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            jsonObjectReturn = CommonAppWsUtils.get("building/building_get");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
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
            jsonObjectReturn = CommonAppWsUtils.get("building/building_get_by_id/" + id);
            
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

            jsonObjectReturn = CommonAppWsUtils.postWithMultiValueMap(parametersMap, "building/building_delete_by_id");
        }
        catch(Exception e) {
            e.printStackTrace();
             
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }

}
