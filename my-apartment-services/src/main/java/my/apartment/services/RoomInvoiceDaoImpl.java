package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import my.apartment.common.CommonUtils;
import my.apartment.common.CommonWsDb;
import my.apartment.model.RoomInvoice;


public class RoomInvoiceDaoImpl implements RoomInvoiceDao {
    
    @Override
    public Boolean create(RoomInvoice roomInvoice) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultSave = Boolean.TRUE;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "INSERT INTO room_invoice "
                    + "(invoice_no, " //1
                    + "invoice_date, " //2
                    + "month, " //3
                    + "year, " //4
                    + "room_id, "  //5
                    + "electricity_previous_meter, " //6
                    + "electricity_present_meter, " //7
                    + "electricity_charge_per_unit, " //8
                    + "electricity_usage_unit, " //9
                    + "electricity_value, " //10
                    + "electricity_use_minimun_unit_calculate, " //11
                    + "water_previous_meter, " //12
                    + "water_present_meter, "  //13
                    + "water_charge_per_unit, "  //14
                    + "water_usage_unit, " //15
                    + "water_value, " //16
                    + "water_use_minimun_unit_calculate, " //17
                    + "status, "  //18
                    + "description, "  //19
                    + "receipt_id, " //20
                    + "created_date) " //21
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, "
                    + "?)"; 
            
            String invoiceNoString = CommonUtils.getCurrentYearString() 
                    + CommonUtils.getCurrentMonthString()
                    + CommonUtils.getCurrentDayString()
                    + CommonUtils.getZeroFillWithNumber(roomInvoice.getRoomId(), 4);
            
            ps = con.prepareStatement(stringQuery);
            ps.setString(1, invoiceNoString);
            ps.setString(2, CommonWsDb.getNowDateString());
            ps.setInt(3, roomInvoice.getMonth());
            ps.setInt(4, roomInvoice.getYear());
            ps.setInt(5, roomInvoice.getRoomId());
            
            ps.setString(6, roomInvoice.getElectricityPreviousMeter());
            ps.setString(7, roomInvoice.getElectricityPresentMeter());
            ps.setBigDecimal(8, roomInvoice.getElectricityChargePerUnit());
            ps.setInt(9, roomInvoice.getElectricityUsageUnit());
            ps.setBigDecimal(10, roomInvoice.getElectricityValue());
            ps.setInt(11, CommonWsDb.getIntFromBoolean(roomInvoice.getElectricityUseMinimunUnitCalculate()));
            
            ps.setString(12, roomInvoice.getWaterPreviousMeter());
            ps.setString(13, roomInvoice.getWaterPresentMeter());
            ps.setBigDecimal(14, roomInvoice.getWaterChargePerUnit());
            ps.setInt(15, roomInvoice.getWaterUsageUnit());
            ps.setBigDecimal(16, roomInvoice.getWaterValue());
            ps.setInt(17, CommonWsDb.getIntFromBoolean(roomInvoice.getWaterUseMinimunUnitCalculate()));
            ps.setInt(18, roomInvoice.getStatus());
            ps.setString(19, roomInvoice.getDescription());

            if(roomInvoice.getReceiptId() == null) {
                ps.setNull(20, java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(20, roomInvoice.getReceiptId());
            }

            ps.setString(21, CommonWsDb.getNowDateString());
            
            Integer effectRow = ps.executeUpdate();
            
            resultSave = effectRow > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultSave = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomInvoiceDaoImpl.class.getName());
        }
        
        return resultSave;
    }
    
}
