package my.apartment.controllers;

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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
    
    @Autowired
    private MessageSource messageSource;
    
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
        
        String currentMonth = CommonUtils.getPreviousMonthYear(Integer.parseInt(CommonUtils.getCurrentMonthString(), 10),
                Integer.parseInt(CommonUtils.getCurrentYearString(), 10)).get("month").toString();
        
        String currentYear = CommonUtils.getPreviousMonthYear(Integer.parseInt(CommonUtils.getCurrentMonthString(), 10),
                Integer.parseInt(CommonUtils.getCurrentYearString(), 10)).get("year").toString();
        
        
        
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
            
            Object[] objectReturn = new Object[3]; //1.all room number, 2.check in, 3.not check in
            Object[] objectAllroom = new Integer[jsonArrayResultBuilding.length()];
            Object[] objectCheckIn = new Integer[jsonArrayResultBuilding.length()];
            Object[] objectNotCheckIn = new Integer[jsonArrayResultBuilding.length()];
            
            for(Integer i = 0; i <= jsonArrayResultBuilding.length() - 1; i++) {
                JSONObject j = jsonArrayResultBuilding.getJSONObject(i);

                String buildingIdString = JsonObjectUtils.getDataStringWithEmpty("id", j);
                String buildingName = JsonObjectUtils.getDataStringWithEmpty("name", j);
                
                arrayCategories[i] = buildingName;

                
                JSONObject resultRoomByBuilding = CommonAppWsUtils.get("room/room_get_by_building_id/" + buildingIdString);
                JSONArray jsonArrayResultRoomByBuilding = resultRoomByBuilding.getJSONArray(CommonString.DATA_STRING);
                arrayRoomNumber[i] = jsonArrayResultRoomByBuilding.length();
                
                
                JSONObject resultCurrentCheckInByBuilding = CommonAppWsUtils.get("room/get_current_check_in_by_building_id/" + buildingIdString);
                JSONArray jsonArrayResultCurrentCheckInByBuilding = resultCurrentCheckInByBuilding.getJSONArray(CommonString.DATA_STRING);
                
                objectAllroom[i] = jsonArrayResultRoomByBuilding.length();
                objectCheckIn[i] = jsonArrayResultCurrentCheckInByBuilding.length();
                objectNotCheckIn[i] = jsonArrayResultRoomByBuilding.length() - jsonArrayResultCurrentCheckInByBuilding.length();
            }
            
            objectReturn[0] = objectAllroom;
            objectReturn[1] = objectCheckIn;
            objectReturn[2] = objectNotCheckIn;

            jsonObjectReturn.put("categories", arrayCategories)
                    .put(CommonString.DATA_STRING, objectReturn)
                    .put("data_", objectReturn);

        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }

        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param buildingId
     * @param month
     * @param year
     * @param response
     * @return String
     */
    @RequestMapping(value = "/get_invoice_by_building_month_chart.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getInvoiceByBuildingMonthChart(
            @RequestParam("building_id") String buildingId,
            @RequestParam("month") String month,
            @RequestParam("year") String year,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            jsonObjectReturn = CommonAppWsUtils.get("room_invoice/get_all_room_invoice_month_year/" + buildingId + "/" + month + "/" + year);
            
            JSONObject dataReturn = new JSONObject();
            
            dataReturn.put(CommonString.DATA_STRING, this.getInvoiceByBuildingMonthChartGenerateData(jsonObjectReturn));
            
            return dataReturn.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param jsonObject
     * @return Object[]
     */
    private Object[] getInvoiceByBuildingMonthChartGenerateData(JSONObject jsonObject) {
        JSONArray data = new JSONArray(jsonObject.get(CommonString.DATA_STRING).toString());
        
        Integer countStatusZero = 0;
        Integer countStatusOne = 0;
        Integer counterStatusTwo = 0;
        for(Integer i = 0; i < data.length(); i++) {
            JSONObject j = data.getJSONObject(i);

            switch (j.getInt("status")) {
                case 0:
                    countStatusZero = countStatusZero + 1;
                    break;
                case 1:
                    countStatusOne = countStatusOne + 1;
                    break;
                case 2:
                    counterStatusTwo = counterStatusTwo + 1;
                    break;
                default:
                    break;
            }
        }
        
        Object[] arrayObject = new Object[3];
        
        Object[] objectCancel = new Object[2];
        objectCancel[0] = messageSource.getMessage("common.cancel", null, LocaleContextHolder.getLocale());
        objectCancel[1] = countStatusZero;
        arrayObject[0] = objectCancel;

        Object[] objectUnbilled = new Object[2];
        objectUnbilled[0] = messageSource.getMessage("room.invoice.unpaid", null, LocaleContextHolder.getLocale());
        objectUnbilled[1] = countStatusOne;
        arrayObject[1] = objectUnbilled;

        Object[] objectBilled = new Object[2];
        objectBilled[0] = messageSource.getMessage("room.invoice.paid", null, LocaleContextHolder.getLocale());
        objectBilled[1] = counterStatusTwo;
        arrayObject[2] = objectBilled;

        return arrayObject;
    }
    
    /**
     * 
     * @param buildingId
     * @param month
     * @param year
     * @param response
     * @return String
     */
    @RequestMapping(value = "/get_receipt_by_building_month_chart.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getReceiptByBuildingMonthChart(
            @RequestParam("building_id") String buildingId,
            @RequestParam("month") String month,
            @RequestParam("year") String year,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            jsonObjectReturn = CommonAppWsUtils.get("room_receipt/get_all_room_receipt_month_year/" + buildingId + "/" + month + "/" + year);
            
            JSONObject dataReturn = new JSONObject();
            
            dataReturn.put(CommonString.DATA_STRING, this.getReceiptByBuildingMonthChartGenerateData(jsonObjectReturn));
            
            return dataReturn.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param jsonObject
     * @return Object
     */
    private Object[] getReceiptByBuildingMonthChartGenerateData(JSONObject jsonObject) {
        JSONArray data = new JSONArray(jsonObject.get(CommonString.DATA_STRING).toString());
        
        Integer countStatusZero = 0;
        Integer countStatusOne = 0;
        for(Integer i = 0; i < data.length(); i++) {
            JSONObject j = data.getJSONObject(i);

            switch (j.getInt("status")) {
                case 0:
                    countStatusZero = countStatusZero + 1;
                    break;
                case 1:
                    countStatusOne = countStatusOne + 1;
                    break;
                default:
                    break;
            }
        }
        
        Object[] arrayObject = new Object[2];
        
        Object[] objectCancel = new Object[2];
        objectCancel[0] = messageSource.getMessage("common.cancel", null, LocaleContextHolder.getLocale());
        objectCancel[1] = countStatusZero;
        arrayObject[0] = objectCancel;

        Object[] objectReceipt = new Object[2];
        objectReceipt[0] = messageSource.getMessage("room.receipt", null, LocaleContextHolder.getLocale());
        objectReceipt[1] = countStatusOne;
        arrayObject[1] = objectReceipt;


        return arrayObject;
    }
    
    /**
     * 
     * @param buildingId
     * @param response
     * @return String
     */
    @RequestMapping(value = "/get_notice_check_out_by_building_data_list.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getNoticeCheckOutByBuildingDataList(
            @RequestParam("building_id") String buildingId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            jsonObjectReturn = CommonAppWsUtils.get("room/get_notice_check_out_by_building_data_list/" + buildingId);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }

        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param buildingId
     * @param response
     * @return String
     */
    @RequestMapping(value = "/get_room_data_by_building_data_list.html", method = {RequestMethod.GET})
    @ResponseBody
    public String getRoomDataByBuildingDataList(
            @RequestParam("building_id") String buildingId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        
        try {
            jsonObjectReturn = CommonAppWsUtils.get("room/get_room_data_by_building_data_list/" + buildingId);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
}
