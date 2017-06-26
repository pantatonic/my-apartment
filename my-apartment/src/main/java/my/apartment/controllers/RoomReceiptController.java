package my.apartment.controllers;

import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.JsonObjectUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomReceiptController {
    
    @Autowired  
    private MessageSource messageSource;
    
    @RequestMapping(value = "/create_room_receipt.html", method = {RequestMethod.POST})
    @ResponseBody
    public String createRoomReceipt(
            @RequestParam("room_invoice_id") String roomInvoiceId,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        CommonAppUtils.setResponseHeader(response);
        System.out.println("-- C");
        System.out.println(roomInvoiceId);
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("room_invoice_id", roomInvoiceId);
            
            jsonObjectReturn = CommonAppWsUtils.postWithMultiValueMap(parametersMap, "room_receipt/create");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
}
