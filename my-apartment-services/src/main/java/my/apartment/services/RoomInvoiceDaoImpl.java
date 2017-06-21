package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import my.apartment.common.CommonUtils;
import my.apartment.common.CommonWsDb;
import my.apartment.common.CommonWsUtils;
import my.apartment.model.RoomInvoice;


public class RoomInvoiceDaoImpl implements RoomInvoiceDao {
    
    @Override
    public Boolean create(RoomInvoice roomInvoice) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultSave = Boolean.TRUE;
        
        try {
            System.out.println("-- create --");
            System.out.println(roomInvoice.getRoomId());
            System.out.println("");
            
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
            
            String invoiceNoString = CommonWsUtils.ROOM_INVOICE_ABBREVIATION 
                    + CommonWsUtils.getTimestampString()
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
    
    @Override
    public List<RoomInvoice> getRoomInvoiceByRoomIdMonthYear(Integer roomId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomInvoice> roomInvoicesReturn = new ArrayList<RoomInvoice>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT * FROM room_invoice "
                    + "WHERE room_invoice.room_id = ? "
                    + "AND room_invoice.month = ? "
                    + "AND room_invoice.year = ? "
                    + "AND status IN (1,2) "
                    + "LIMIT 0, 1";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomInvoice roomInvoice = new RoomInvoice();
             
                roomInvoice.setId(rs.getInt("id"));
                roomInvoice.setInvoiceNo(rs.getString("invoice_no"));
                roomInvoice.setInvoiceDate(rs.getDate("invoice_date"));
                roomInvoice.setMonth(rs.getInt("month"));
                roomInvoice.setYear(rs.getInt("year"));
                roomInvoice.setRoomId(rs.getInt("room_id"));
                roomInvoice.setElectricityPreviousMeter(rs.getString("electricity_previous_meter"));
                roomInvoice.setElectricityPresentMeter(rs.getString("electricity_present_meter"));
                roomInvoice.setElectricityChargePerUnit(rs.getBigDecimal("electricity_charge_per_unit"));
                roomInvoice.setElectricityUsageUnit(rs.getInt("electricity_usage_unit"));
                roomInvoice.setElectricityValue(rs.getBigDecimal("electricity_value"));
                roomInvoice.setElectricityUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("electricity_use_minimun_unit_calculate")));
                roomInvoice.setWaterPreviousMeter(rs.getString("water_previous_meter"));
                roomInvoice.setWaterPresentMeter(rs.getString("water_present_meter"));
                roomInvoice.setWaterChargePerUnit(rs.getBigDecimal("water_charge_per_unit"));
                roomInvoice.setWaterUsageUnit(rs.getInt("water_usage_unit"));
                roomInvoice.setWaterValue(rs.getBigDecimal("water_value"));
                roomInvoice.setWaterUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("water_use_minimun_unit_calculate")));
                roomInvoice.setStatus(rs.getInt("status"));
                roomInvoice.setDescription(rs.getString("description"));
                roomInvoice.setReceiptId(rs.getInt("receipt_id"));
                roomInvoice.setCreatedDate(rs.getDate("created_date"));
                roomInvoice.setUpdatedDate(rs.getDate("updated_date"));
                
                roomInvoicesReturn.add(roomInvoice);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomInvoiceDaoImpl.class.getName());
        }
        
        return roomInvoicesReturn;
    }
    
    @Override
    public List<RoomInvoice> getRoomInvoiceMonthYear(Integer buildingId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomInvoice> roomInvoicesReturn = new ArrayList<RoomInvoice>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT room_invoice.* " +
                "FROM room_invoice JOIN room ON room_invoice.room_id = room.id " +
                "JOIN building ON room.building_id = building.id " +
                "WHERE building.id = ? " +
                "AND room_invoice.month = ? " +
                "AND room_invoice.year = ? " +
                "AND room_invoice.status IN (1,2)";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, buildingId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomInvoice roomInvoice = new RoomInvoice();
             
                roomInvoice.setId(rs.getInt("id"));
                roomInvoice.setInvoiceNo(rs.getString("invoice_no"));
                roomInvoice.setInvoiceDate(rs.getDate("invoice_date"));
                roomInvoice.setMonth(rs.getInt("month"));
                roomInvoice.setYear(rs.getInt("year"));
                roomInvoice.setRoomId(rs.getInt("room_id"));
                roomInvoice.setElectricityPreviousMeter(rs.getString("electricity_previous_meter"));
                roomInvoice.setElectricityPresentMeter(rs.getString("electricity_present_meter"));
                roomInvoice.setElectricityChargePerUnit(rs.getBigDecimal("electricity_charge_per_unit"));
                roomInvoice.setElectricityUsageUnit(rs.getInt("electricity_usage_unit"));
                roomInvoice.setElectricityValue(rs.getBigDecimal("electricity_value"));
                roomInvoice.setElectricityUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("electricity_use_minimun_unit_calculate")));
                roomInvoice.setWaterPreviousMeter(rs.getString("water_previous_meter"));
                roomInvoice.setWaterPresentMeter(rs.getString("water_present_meter"));
                roomInvoice.setWaterChargePerUnit(rs.getBigDecimal("water_charge_per_unit"));
                roomInvoice.setWaterUsageUnit(rs.getInt("water_usage_unit"));
                roomInvoice.setWaterValue(rs.getBigDecimal("water_value"));
                roomInvoice.setWaterUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("water_use_minimun_unit_calculate")));
                roomInvoice.setStatus(rs.getInt("status"));
                roomInvoice.setDescription(rs.getString("description"));
                roomInvoice.setReceiptId(rs.getInt("receipt_id"));
                roomInvoice.setCreatedDate(rs.getDate("created_date"));
                roomInvoice.setUpdatedDate(rs.getDate("updated_date"));
                
                roomInvoicesReturn.add(roomInvoice);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomInvoiceDaoImpl.class.getName());
        }
        
        return roomInvoicesReturn;
    }
    
}
