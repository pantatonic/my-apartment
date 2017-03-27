package my.apartment.controllers;

import my.apartment.common.CommonString;
import my.apartment.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {
    
    @RequestMapping(value = "/room.html", method = {RequestMethod.GET})
    public ModelAndView roomIndex() {
        ModelAndView modelAndView = new ModelAndView("room/room_index/room_index");
        
        JSONObject resultGetBuilding = this.getBuilding();
        
        modelAndView.addObject("test", resultGetBuilding.get("data"));
        
        /*JSONArray ja = new JSONArray(resultGetBuilding.getJSONObject("data"));
        System.out.println("-----------");
        System.out.println(resultGetBuilding.getJSONObject("data"));
        System.out.println("-----------");
        System.out.println("===========");
        System.out.println(ja);
        System.out.println("===========");*/
        
        JSONArray ja = new JSONArray(resultGetBuilding.get("data").toString());
        
        for(Integer i = 0; i < ja.length(); i ++) {
            System.out.println(ja.getJSONObject(i));
        }
        
        return modelAndView;
    }
    
    private JSONObject getBuilding() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.getForObject(ServiceDomain.WS_URL + "building/building_get", String.class);
            
            jsonObjectReturn = new JSONObject(resultWs);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn;
    }
    
}
