package my.apartment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String buildingSave() {
        return "xxx";
    }

}
