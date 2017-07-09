package my.apartment.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import my.apartment.common.CommonAppUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


public class RoomReceiptPdf {
    
    private final MessageSource messageSource;
    private final String useFontResource = "thai_fonts/sarabun/THSarabun.ttf";
    private final String useFontResourceBold = "thai_fonts/sarabun/THSarabun Bold.ttf";
    
    public RoomReceiptPdf(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    private String getMessageSourcesString(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
    
    private String getStringNoneJsonObject(String key, JSONObject jsonObject) {
        String str = CommonAppUtils.getStringJsonObject(key, jsonObject);
        
        if(str == null) {
            return "-";
        }
        else {
            return str;
        }
    }
    
    private Font getFontHeader() throws DocumentException, IOException {
        Font fontHeader = new Font(BaseFont.createFont(this.useFontResource,
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 13);
        
        return fontHeader;
    }
    
    private Font getFontHeadTable() throws DocumentException, IOException {
        Font fontDetail = new Font(BaseFont.createFont(this.useFontResourceBold,
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12);
        
        return fontDetail;
    }
    
    private Font getFontGrandTotal() throws DocumentException, IOException {
        Font fontDetail = new Font(BaseFont.createFont(this.useFontResourceBold,
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14);
        fontDetail.setColor(Color.WHITE);
        
        return fontDetail;
    }
    
    private Font getFontDetail() throws DocumentException, IOException {
        Font fontDetail = new Font(BaseFont.createFont(this.useFontResource,
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12);
        
        return fontDetail;
    }
    
    private PdfPTable getTableHeadReceiptNo(JSONObject jsonObject) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {35f, 35f});
        
        PdfPCell cell = new PdfPCell();
        
        cell.setPhrase(new Phrase(
                this.getMessageSourcesString("room.receipt_no") + " : " + jsonObject.getString("receiptNo") + "\n"
                + this.getMessageSourcesString("apartment.building") + " : " + jsonObject.getString("buildingName") + "\n"
                + this.getMessageSourcesString("building.room") + " : " + jsonObject.getString("roomNo") + "\n"
                + this.getMessageSourcesString("room.invoice.check_in_name") + " : " 
                + this.getStringNoneJsonObject("payer", jsonObject) + " "
                , this.getFontHeader()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("_Receipt Date_" + " : " + jsonObject.getString("createdDate"), this.getFontHeader()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10);

        return table;
    }
    
    public void generateRoomReceiptPdf(
            Document document, 
            PdfWriter writer, 
            JSONArray jsonArrayData
    ) throws DocumentException, IOException {
        document.setPageSize(PageSize.A5.rotate());
        
        document.setMargins(50f,50f,5f,10f);
        document.open();
        
        for(Integer i = 0; i < jsonArrayData.length(); i++) {
            JSONObject j = jsonArrayData.getJSONObject(i);
            
            PdfPTable tableInvoiceNo = this.getTableHeadReceiptNo(j);
            
            document.add(tableInvoiceNo);
            
            document.newPage();
        }
        
        document.close();
    }
}
