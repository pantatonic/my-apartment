package my.apartment.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import my.common.ServiceDomain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    private final String privateParamString = "This is private parameter string";
    private static final String WS_URI = ServiceDomain.WS_URL + "test";

    @RequestMapping(value = "/test.html", method = {RequestMethod.GET})
    @ResponseBody
    public String test() {
        return "This is Test Controller";
    }

    @RequestMapping(value = "/test_view.html", method = {RequestMethod.GET})
    public ModelAndView testView(Locale locale) {
        ModelAndView modelAndView = new ModelAndView("test");

        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");

        modelAndView.addObject("privateParamString", this.privateParamString)
                .addObject("text1", "This is text1 parameter")
                .addObject("strings", strings);

        RestTemplate restTemplate = new RestTemplate();
        String resultTestWs = restTemplate.getForObject(TestController.WS_URI + "/get_all_test", String.class);
        JSONObject resultTestWsJsonObject = new JSONObject(resultTestWs);

        JSONArray jsonArrayData = resultTestWsJsonObject.getJSONArray("data");

        for (Integer i = 0; i <= jsonArrayData.length() - 1; i++) {
            JSONObject j = jsonArrayData.getJSONObject(i);

            System.out.println(j.getString("name"));
        }

        modelAndView.addObject("jsonArrayData", jsonArrayData);

        RestTemplate rt = new RestTemplate();
        String resultRt = rt.getForObject(TestController.WS_URI + "/get_test_by_id/1", String.class);
        JSONObject resultRtJsonObject = new JSONObject(resultRt);

        JSONArray jsona = resultRtJsonObject.getJSONArray("data");
        modelAndView.addObject("jsona", jsona);
        
        locale = LocaleContextHolder.getLocale();
        //LocaleContextHolder.setLocale(new Locale("en_US"));
        
        locale = LocaleContextHolder.getLocale();

        
        System.out.println(locale + " xxx");

        return modelAndView;
    }

    @RequestMapping(value = "/test_tiles.html", method = {RequestMethod.GET})
    public ModelAndView testTiles() {
        return new ModelAndView("test_tiles");
    }
}
