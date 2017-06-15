package my.apartment.controllers;

import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.CommonString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RoomInvoice {
    
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
        
        String currentMonth = CommonAppUtils.getCurrentMonthString();
        String currentYear = CommonAppUtils.getCurrentYearString();
        
        modelAndView.addObject("buildingList", jsonArrayBuilding);
        modelAndView.addObject("currentYearMonth", currentYear + "-" + currentMonth);
        
        return modelAndView;
    }
    
}
