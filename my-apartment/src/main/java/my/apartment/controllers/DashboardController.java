package my.apartment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
    
    @RequestMapping(value = "/dashboard.html", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard/dashboard");
        
        return modelAndView;
    }
    
}
