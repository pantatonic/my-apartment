package my.apartment.pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import my.apartment.common.CommonAppUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


public class RoomInvoicePdf {
    
    private final MessageSource messageSource;
    private final String useFontResource = "thai_fonts/sarabun/THSarabun.ttf";
    private final String useFontResourceBold = "thai_fonts/sarabun/THSarabun Bold.ttf";
    
    public RoomInvoicePdf(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    private String getMessageSourcesString(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
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
    
    private Color getCellDetailBorderGrey() {
        return new Color(130, 130, 130);
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
    
    
    private PdfPTable getTableHeadElectricity() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {55f, 15f});

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(234, 234, 234));
        
        cell.setPhrase(new Phrase(this.getMessageSourcesString("electricity_water_meter.electricity_meter"), this.getFontHeadTable()));
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
    
    private PdfPTable getTableHeadWater() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {55f, 15f});

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(234, 234, 234));
        
        cell.setPhrase(new Phrase(this.getMessageSourcesString("electricity_water_meter.water_meter"), this.getFontHeadTable()));
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
    
    private PdfPTable getTableHeadRoom() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {55f, 15f});

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(234, 234, 234));

        cell.setPhrase(new Phrase(this.getMessageSourcesString("room.invoice.room_price"), this.getFontHeadTable()));
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
    
    private PdfPTable getTableHeadInvoiceNo(JSONObject jsonObject) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {35f, 35f});
        
        PdfPCell cell = new PdfPCell();
        
        cell.setPhrase(new Phrase(
                this.getMessageSourcesString("room.invoice.invoice_no") + " : " + jsonObject.getString("invoiceNo") + "\n"
                + this.getMessageSourcesString("apartment.building") + " : " + jsonObject.getString("buildingName") + "\n"
                + this.getMessageSourcesString("building.room") + " : " + jsonObject.getString("roomNo")
                //+ this.getStringNoneJsonObject("checkInName", jsonObject)
                
                , this.getFontHeader()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell.setPhrase(new Phrase(this.getMessageSourcesString("room.invoice.invoice_date") + " : " + jsonObject.getString("invoiceDate"), this.getFontHeader()));
        cell.setMinimumHeight(30f);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10);

        return table;
    }
    
    private BigDecimal calculateGrandTotal(JSONObject jsonObject) {
        BigDecimal grandTotal = jsonObject.getBigDecimal("electricityValue")
                .add(jsonObject.getBigDecimal("waterValue"))
                .add(jsonObject.getBigDecimal("roomPricePerMonth"));

        return grandTotal;
    }
    
    public void generateRoomInvoicePdf(
            Document document, 
            PdfWriter writer, 
            JSONArray jsonArrayData
    ) throws DocumentException, IOException {
        document.setPageSize(PageSize.A5.rotate());

        document.setMargins(50f,50f,5f,10f);
        document.open();

        /*Paragraph headerx = new Paragraph(
                new Chunk(this.getMessageSourcesString(messageSource, "room.reserve"),
                    fontx)
        );*/


        for(Integer i = 0; i < jsonArrayData.length(); i++) {
            JSONObject j = jsonArrayData.getJSONObject(i);
            
            String invoiceNo = j.getString("invoiceNo");

            /*Paragraph header = new Paragraph(
                    new Chunk(j.getString("buildingName"), this.getFontHeader())
            );*/
            
            String previousMeterString = this.getMessageSourcesString("electricity_water_meter.previous_meter");
            String electricMeterString = this.getMessageSourcesString("electricity_water_meter.electricity_meter");
            String pricePerUnitString = this.getMessageSourcesString("room.invoice.price_per_unit");
            String usageUnitString = this.getMessageSourcesString("room.invoice.usage_unit");
            String valueString = this.getMessageSourcesString("room.invoice.value");
            
            String roomPriceString = this.getMessageSourcesString("room.invoice.room_price");
            
            
            PdfPTable tableInvoiceNo = this.getTableHeadInvoiceNo(j);
            
            
            /** electricity table */
            PdfPTable tableElectricity = this.getTableHeadElectricity();
            PdfPCell cellElectricity = new PdfPCell();
            cellElectricity.setBorderColor(this.getCellDetailBorderGrey());

            cellElectricity.setPhrase(new Phrase(
                    previousMeterString + " : "
                    + j.getString("electricityPreviousMeter") + ", "
                    + electricMeterString + " : "
                    + j.getString("electricityPresentMeter") + " \n"
                    + pricePerUnitString + "\n"
                    + usageUnitString + "\n"
                    + valueString
                    , this.getFontDetail()));
            cellElectricity.setMinimumHeight(30f);
            cellElectricity.setPadding(5);
            cellElectricity.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellElectricity.setVerticalAlignment(Element.ALIGN_TOP);
            tableElectricity.addCell(cellElectricity);

            cellElectricity.setPhrase(new Phrase(
                    " \n"
                    + j.getInt("electricityChargePerUnit") + "\n"
                    + j.getInt("electricityUsageUnit") + "\n"
                    + j.getBigDecimal("electricityValue")
                    , this.getFontDetail()));
            cellElectricity.setMinimumHeight(30f);
            cellElectricity.setPadding(5);
            cellElectricity.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellElectricity.setVerticalAlignment(Element.ALIGN_TOP);
            tableElectricity.addCell(cellElectricity);
            tableElectricity.setSpacingAfter(10);
            
            
            /** water table */
            PdfPTable tableWater = this.getTableHeadWater();
            PdfPCell cellWater = new PdfPCell();
            cellWater.setBorderColor(this.getCellDetailBorderGrey());
            
            cellWater.setPhrase(new Phrase(
                    previousMeterString + " : "
                    + j.getString("waterPreviousMeter") + ", "
                    + electricMeterString + " : "
                    + j.getString("waterPresentMeter") + " \n"
                    + pricePerUnitString + "\n"
                    + usageUnitString + "\n"
                    + valueString
                    , this.getFontDetail()));
            cellWater.setMinimumHeight(30f);
            cellWater.setPadding(5);
            cellWater.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellWater.setVerticalAlignment(Element.ALIGN_TOP);
            tableWater.addCell(cellWater);
            
            cellWater.setPhrase(new Phrase(
                    " \n"
                    + j.getInt("waterChargePerUnit") + "\n"
                    + j.getInt("waterUsageUnit") + "\n"
                    + j.getBigDecimal("waterValue")
                    , this.getFontDetail()));
            cellWater.setMinimumHeight(30f);
            cellWater.setPadding(5);
            cellWater.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellWater.setVerticalAlignment(Element.ALIGN_TOP);
            tableWater.addCell(cellWater);
            tableWater.setSpacingAfter(10);
            
            
            /** room table */
            PdfPTable tableRoom = this.getTableHeadRoom();
            PdfPCell cellRoom = new PdfPCell();
            cellRoom.setBorderColor(this.getCellDetailBorderGrey());
            
            cellRoom.setPhrase(new Phrase(roomPriceString, this.getFontDetail()));
            cellRoom.setMinimumHeight(30f);
            cellRoom.setPadding(5);
            cellRoom.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellRoom.setVerticalAlignment(Element.ALIGN_TOP);
            tableRoom.addCell(cellRoom);
            
            cellRoom.setPhrase(new Phrase(j.getBigDecimal("roomPricePerMonth") + "", this.getFontDetail()));
            cellRoom.setMinimumHeight(30f);
            cellRoom.setPadding(5);
            cellRoom.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellRoom.setVerticalAlignment(Element.ALIGN_TOP);
            tableRoom.addCell(cellRoom);
            tableRoom.setSpacingAfter(10);
            
            
            /** table grand total */
            PdfPTable tableGrandTotal = new PdfPTable(2);
            tableGrandTotal.setWidthPercentage(100.0f);
            tableGrandTotal.setWidths(new float[] {55f, 15f});
            
            PdfPCell cellGrandTotal = new PdfPCell();
            cellGrandTotal.setBackgroundColor(new Color(60,141,188));
            cellGrandTotal.setBorderColor(new Color(60, 141, 188));
            
            cellGrandTotal.setPhrase(new Phrase(this.getMessageSourcesString("common.grand_total"), this.getFontGrandTotal()));
            cellGrandTotal.setMinimumHeight(30f);
            cellGrandTotal.setPadding(5);
            cellGrandTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellGrandTotal.setVerticalAlignment(Element.ALIGN_TOP);
            tableGrandTotal.addCell(cellGrandTotal);

            cellGrandTotal.setPhrase(new Phrase(this.calculateGrandTotal(j) + "", this.getFontGrandTotal()));
            cellGrandTotal.setMinimumHeight(30f);
            cellGrandTotal.setPadding(5);
            cellGrandTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellGrandTotal.setVerticalAlignment(Element.ALIGN_TOP);
            tableGrandTotal.addCell(cellGrandTotal);
            tableGrandTotal.setSpacingAfter(10);
            
            System.out.println("-- pdf --");
            System.out.println(j);
            System.out.println("");
            
            
            Paragraph footer = new Paragraph(
                    new Chunk(
                            this.getMessageSourcesString("room.invoice.check_in_name") + " : " 
                            + this.getStringNoneJsonObject("checkInName", j) + " "
                            + (this.getStringNoneJsonObject("checkInLastname", j).equals("-") ? " " : this.getStringNoneJsonObject("checkInLastname", j))
                            , this.getFontHeader()
                    )
            );
            
            
            document.add(tableInvoiceNo);
            document.add(tableElectricity);
            document.add(tableWater);
            document.add(tableRoom);
            document.add(tableGrandTotal);
            document.add(footer);

            document.newPage();
        }

        document.close();
    }
    
}
