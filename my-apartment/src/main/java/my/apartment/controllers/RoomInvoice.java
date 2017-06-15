package my.apartment.controllers;

import my.apartment.common.CommonAppWsUtils;
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
    
    @RequestMapping(value = "/room_invoice.html", method = {RequestMethod.GET})
    public ModelAndView roomInvoice() {
        ModelAndView modelAndView = new ModelAndView("room_invoice/room_invoice_index/room_invoice_index");
        
        JSONObject resultGetBuilding = CommonAppWsUtils.getBuildingList();
        
        System.out.println(resultGetBuilding);
        
        return modelAndView;
    }
    
}
