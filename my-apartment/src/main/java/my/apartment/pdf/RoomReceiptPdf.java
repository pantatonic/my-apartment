package my.apartment.pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
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
        
        cell.setPhrase(new Phrase(this.getMessageSourcesString("room.receipt_date") + " : " + jsonObject.getString("createdDate"), this.getFontHeader()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10);

        return table;
    }
    
    private PdfPTable getTableHead() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {55f, 15f});
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(234, 234, 234));
        
        cell.setPhrase(new Phrase(this.getMessageSourcesString("room.invoice.description"), this.getFontHeadTable()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cell);
        
        cell.setPhrase(new Phrase(this.getMessageSourcesString("common.total"), this.getFontHeadTable()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(cell);
        
        return table;
    }
    
    private Color getCellDetailBorderGrey() {
        return new Color(130, 130, 130);
    }
    
    private Phrase getPhraseDescription(JSONObject jsonObject) throws DocumentException, IOException {
        Phrase phrase = new Phrase();
        
        String electricityUseMinimumUnitCalculateString = "";
        if(jsonObject.getBoolean("electricityUseMinimunUnitCalculate")) {
            electricityUseMinimumUnitCalculateString = "(" + this.getMessageSourcesString("room.invoice.if_electricity_usage_unit_less_than_")
                    + " " + jsonObject.getInt("minElectricityUnit")
                    + ". " + this.getMessageSourcesString("room.invoice.value_is_")
                    + " " + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("minElectricityCharge"))
                    + ")";
        }

        String waterUseMinimumUnitCalculateString = "";
        if(jsonObject.getBoolean("waterUseMinimunUnitCalculate")) {
            waterUseMinimumUnitCalculateString = "(" + this.getMessageSourcesString("room.invoice.if_water_usage_unit_less_than_")
                    + " " + jsonObject.getInt("minWaterUnit")
                    + ". " + this.getMessageSourcesString("room.invoice.value_is_")
                    + " " + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("minWaterCharge"))
                    + ")";
        }
        

        phrase.add(
                new Chunk(this.getMessageSourcesString("electricity_water_meter.electricity_meter") + "\n", this.getFontHeadTable())
        );
        phrase.add(new Chunk(
                        this.getMessageSourcesString("electricity_water_meter.previous_meter") + " : " + jsonObject.getString("electricityPreviousMeter") 
                        + ", "
                        + this.getMessageSourcesString("electricity_water_meter.electricity_meter") + " : " + jsonObject.getString("electricityPresentMeter") + "\n"
                        + this.getMessageSourcesString("room.invoice.price_per_unit") + " : " + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("electricityChargePerUnit")) + "\n"
                        + this.getMessageSourcesString("room.invoice.usage_unit") + " : " + jsonObject.getInt("electricityUsageUnit") + "\n"
                        + this.getMessageSourcesString("room.invoice.value") + " " + electricityUseMinimumUnitCalculateString
                        + "\n\n"
                        , this.getFontDetail()));
        
        phrase.add(
                new Chunk(this.getMessageSourcesString("electricity_water_meter.water_meter") + "\n", this.getFontHeadTable())
        );
        phrase.add(
                new Chunk(
                        this.getMessageSourcesString("electricity_water_meter.previous_meter") + " : " + jsonObject.getString("waterPreviousMeter")
                        + ", "
                        + this.getMessageSourcesString("electricity_water_meter.water_meter") + " : " +jsonObject.getString("waterPresentMeter") + "\n"
                        + this.getMessageSourcesString("room.invoice.price_per_unit") + " : " + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("waterChargePerUnit")) + "\n"
                        + this.getMessageSourcesString("room.invoice.usage_unit") + " : " + jsonObject.getInt("waterUsageUnit") + "\n"
                        + this.getMessageSourcesString("room.invoice.value") + " " + waterUseMinimumUnitCalculateString
                        + "\n\n"
                        , this.getFontDetail())
        );
        
        phrase.add(
                new Chunk(this.getMessageSourcesString("room.invoice.room_price"), this.getFontHeadTable())
        );
        
        return phrase;
    }
    
    private Phrase getPhraseTotal(JSONObject jsonObject) throws DocumentException, IOException {
        Phrase phrase = new Phrase();
        
        phrase.add(
                new Chunk("\n\n\n\n"
                        + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("electricityValue"))
                        + "\n\n\n\n\n\n"
                        + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("waterValue"))
                        + "\n\n"
                        + CommonAppUtils.decimalFormat(jsonObject.getBigDecimal("roomPricePerMonth"))
                        , this.getFontDetail())
        );
        
        return phrase;
    }
    
    private BigDecimal calculateGrandTotal(JSONObject jsonObject) {
        BigDecimal grandTotal = jsonObject.getBigDecimal("electricityValue")
                .add(jsonObject.getBigDecimal("waterValue"))
                .add(jsonObject.getBigDecimal("roomPricePerMonth"));

        return grandTotal;
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
            
            PdfPTable tableReceiptNo = this.getTableHeadReceiptNo(j);
            
            PdfPTable table = this.getTableHead();
            PdfPCell cell = new PdfPCell();
            cell.setBorderColor(this.getCellDetailBorderGrey());
            

            cell.setPhrase(this.getPhraseDescription(j));
            cell.setMinimumHeight(30f);
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);
            
            cell.setPhrase(this.getPhraseTotal(j));
            cell.setMinimumHeight(30f);
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);
            
            PdfPCell cellGrandTotal = new PdfPCell();
            cellGrandTotal.setBackgroundColor(new Color(0, 166, 90));
            cellGrandTotal.setBorderColor(new Color(0, 166, 90));
            
            cellGrandTotal.setPhrase(new Phrase(this.getMessageSourcesString("common.grand_total"), this.getFontGrandTotal()));
            cellGrandTotal.setMinimumHeight(30f);
            cellGrandTotal.setPadding(5);
            cellGrandTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellGrandTotal.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellGrandTotal);
            
            cellGrandTotal.setPhrase(new Phrase(CommonAppUtils.decimalFormat(this.calculateGrandTotal(j)) + "", this.getFontGrandTotal()));
            cellGrandTotal.setMinimumHeight(30f);
            cellGrandTotal.setPadding(5);
            cellGrandTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellGrandTotal.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cellGrandTotal);
            table.setSpacingBefore(30);
            table.setSpacingAfter(30);     
            
            Paragraph refInvoiceNo = new Paragraph(
                new Chunk(this.getMessageSourcesString("room.receipt_reference_invoice_no") + " : " + j.getString("invoiceNo")
                        , this.getFontDetail())
            );
            
            document.add(tableReceiptNo);
            document.add(table);
            document.add(refInvoiceNo);
            
            document.newPage();
        }
        
        document.close();
    }
}
