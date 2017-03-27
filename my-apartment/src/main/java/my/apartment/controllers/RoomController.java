package my.apartment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {
    
    @RequestMapping(value = "/room.html", method = {RequestMethod.GET})
    public ModelAndView roomIndex() {
        ModelAndView modelAndView = new ModelAndView("room/room_index/room_index");
        
        return modelAndView;
    }
    
}
