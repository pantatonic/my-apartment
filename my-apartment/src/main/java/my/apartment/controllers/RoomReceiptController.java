package my.apartment.controllers;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import my.apartment.common.CommonAppUtils;
import my.apartment.common.CommonAppWsUtils;
import my.apartment.common.CommonString;
import my.apartment.common.JsonObjectUtils;
import my.apartment.pdf.RoomReceiptPdf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    /**
     * 
     * @param roomReceiptId
     * @param description
     * @param response
     * @return String
     */
    @RequestMapping(value = "/cancel_room_receipt.html", method = {RequestMethod.POST})
    @ResponseBody
    public String cancelRoomReceipt(
            @RequestParam("room_receipt_id") String roomReceiptId,
            @RequestParam("description") String description,
            HttpServletResponse response
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("room_receipt_id", roomReceiptId);
            parametersMap.add("description", description);
            
            jsonObjectReturn = CommonAppWsUtils.postWithMultiValueMap(parametersMap, "room_receipt/cancel");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setControllerError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }

    /**
     * 
     * @param formData
     * @param response
     * @param document
     * @param writer
     * @throws DocumentException
     * @throws IOException 
     */
    @RequestMapping(value = "/pdf_room_receipt.html", method = {RequestMethod.POST})
    public void pdfRoomReceipt(
            @RequestBody MultiValueMap<String, String> formData,
            HttpServletResponse response,
            Document document,
            PdfWriter writer
    ) throws DocumentException, IOException {
        JSONObject jsonObjectReturn = new JSONObject();
        
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        
        PdfWriter.getInstance(document, response.getOutputStream());
        
        try {
            List<String> roomInvoiceIds = formData.get("pdf_room_receipt_id");
            
            String requestJson = new JSONObject()
                    .put(CommonString.DATA_STRING, roomInvoiceIds)
                    .toString();
            
            jsonObjectReturn = CommonAppWsUtils.postWithJsonDataString(requestJson, "room_receipt/post_get_room_receipt_by_id");
            
            JSONArray jsonArrayData = new JSONArray(jsonObjectReturn.get(CommonString.DATA_STRING).toString());

            new RoomReceiptPdf(messageSource).generateRoomReceiptPdf(document, writer, jsonArrayData);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
