package my.apartment.controllers;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuildingController {

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/building.html", method = {RequestMethod.GET})
    public ModelAndView buildingIndex() {

        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(messageSource);
        System.out.println(messageSource.getMessage("common.login", null, locale));
        System.out.println("..........");
        
        ModelAndView modelAndView = new ModelAndView("building/building_index/building_index");

        return modelAndView;
    }

}
