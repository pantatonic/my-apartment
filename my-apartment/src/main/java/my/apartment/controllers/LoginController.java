package my.apartment.controllers;

import javax.servlet.http.HttpSession;
import my.common.CommonString;
import my.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {
    
    private static final String WS_URI = ServiceDomain.WS_URL;
    
    @RequestMapping(value = "/login_process.html", method = {RequestMethod.POST})
    @ResponseBody
    public String loginProcess(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            HttpSession session
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("email", email);
            parametersMap.add("password", password);
            
            RestTemplate restTemplate = new RestTemplate();
            String resultWs = restTemplate.postForObject(LoginController.WS_URI + "login", parametersMap, String.class);
            JSONObject resultWsJsonObject = new JSONObject(resultWs);

            if(resultWsJsonObject.get(CommonString.RESULT_STRING).equals(CommonString.SUCCESS_STRING)) {
                JSONArray jsonArrayData = resultWsJsonObject.getJSONArray("data");
                
                for(Integer i = 0; i <= jsonArrayData.length() - 1; i++) {
                    JSONObject j = jsonArrayData.getJSONObject(i);

                    session.setAttribute("userId", Integer.toString(j.getInt("id")));
                    session.setAttribute("userEmail", j.getString("email"));
                    session.setAttribute("userFirstname", j.getString("firstname"));
                    session.setAttribute("userLastname", j.getString("lastname"));
                    session.setAttribute("userIsAdmin", Integer.toString(j.getInt("isAdmin")));
                    session.setAttribute("userStatus", Integer.toString(j.getInt("status")));
                }
                
                jsonObjectReturn.put(CommonString.RESULT_STRING, resultWsJsonObject.get(CommonString.RESULT_STRING))
                        .put(CommonString.MESSAGE_STRING, resultWsJsonObject.get(CommonString.MESSAGE_STRING));
            }
            else {
                jsonObjectReturn.put(CommonString.RESULT_STRING, resultWsJsonObject.get(CommonString.RESULT_STRING))
                        .put(CommonString.MESSAGE_STRING, resultWsJsonObject.get(CommonString.MESSAGE_STRING));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.CONTROLLER_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @RequestMapping(value = "/logout_process.html", method = {RequestMethod.GET})
    @ResponseBody
    public String logoutProcess(
            HttpSession session
    ) {
        if(session.getAttribute("userId") != null) {
            String sessionFullName = session.getAttribute("userFirstname").toString() + session.getAttribute("userLastname").toString();
            
            session.invalidate();
            
            return "Logout account : " + sessionFullName;
        }
        
        return "...";
    }
    
}
