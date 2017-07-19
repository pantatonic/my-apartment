package my.apartment.controllers;

import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.CommonString;
import my.apartment.common.CommonUtils;
import my.apartment.common.JsonObjectUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
    
    /**
     * 
     * @return 
     */
    @RequestMapping(value = "/dashboard.html", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard/dashboard_index");
        
        JSONObject resultGetBuilding = CommonAppWsUtils.getBuildingList();
        JSONArray jsonArrayBuilding = new JSONArray(resultGetBuilding.get(CommonString.DATA_STRING).toString());
        
        modelAndView.addObject("buildingList", jsonArrayBuilding);
        
        String currentMonth = CommonUtils.getCurrentMonthString();
        String currentYear = CommonUtils.getCurrentYearString();
        
        modelAndView.addObject("currentYearMonth", currentYear + "-" + currentMonth);
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/d.html", method = {RequestMethod.GET})
    public ModelAndView d() {
        return new ModelAndView("dashboard/d");
    }
    
    /**
     * 
     * @param response
     * @return 
     */
    @RequestMapping(value = "/get_room_by_building_chart.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getRoomByBuildingChart(
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            JSONObject resultBuilding = CommonAppWsUtils.get("building/building_get");
            JSONArray jsonArrayResultBuilding = resultBuilding.getJSONArray(CommonString.DATA_STRING);

            Integer categoriesLength = jsonArrayResultBuilding.length();
            
            String[] arrayCategories = new String[categoriesLength];
            Integer[] arrayRoomNumber = new Integer[categoriesLength];
            
            for(Integer i = 0; i <= jsonArrayResultBuilding.length() - 1; i++) {
                JSONObject j = jsonArrayResultBuilding.getJSONObject(i);

                String buildingIdString = JsonObjectUtils.getDataStringWithEmpty("id", j);
                String buildingName = JsonObjectUtils.getDataStringWithEmpty("name", j);
                
                arrayCategories[i] = buildingName;

                
                JSONObject resultRoomByBuilding = CommonAppWsUtils.get("room/room_get_by_building_id/" + buildingIdString);
                JSONArray jsonArrayResultRoomByBuilding = resultRoomByBuilding.getJSONArray(CommonString.DATA_STRING);
                
                arrayRoomNumber[i] = jsonArrayResultRoomByBuilding.length();
            }
            
            jsonObjectReturn.put("categories", arrayCategories)
                .put(CommonString.DATA_STRING, arrayRoomNumber);

        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }

        return jsonObjectReturn.toString();
    }
    
}
