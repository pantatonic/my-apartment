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
                    + "room_price_per_month, " //6
                    + "electricity_previous_meter, " //7
                    + "electricity_present_meter, " //8
                    + "electricity_charge_per_unit, " //9
                    + "electricity_usage_unit, " //10
                    + "electricity_value, " //11
                    + "electricity_use_minimun_unit_calculate, " //12
                    + "water_previous_meter, " //13
                    + "water_present_meter, "  //14
                    + "water_charge_per_unit, "  //15
                    + "water_usage_unit, " //16
                    + "water_value, " //17
                    + "water_use_minimun_unit_calculate, " //18
                    + "status, "  //19
                    + "description, "  //20
                    + "receipt_id, " //21
                    + "created_date) " //22
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, "
                    + "?, ?)"; 
            
            String invoiceNoString = CommonWsUtils.ROOM_INVOICE_ABBREVIATION 
                    + CommonWsUtils.getTimestampString()
                    + CommonUtils.getZeroFillWithNumber(roomInvoice.getRoomId(), 4);
            
            ps = con.prepareStatement(stringQuery);
            ps.setString(1, invoiceNoString);
            ps.setString(2, CommonWsDb.getNowDateString());
            ps.setInt(3, roomInvoice.getMonth());
            ps.setInt(4, roomInvoice.getYear());
            ps.setInt(5, roomInvoice.getRoomId());
            ps.setBigDecimal(6, roomInvoice.getRoomPricePerMonth());
            
            ps.setString(7, roomInvoice.getElectricityPreviousMeter());
            ps.setString(8, roomInvoice.getElectricityPresentMeter());
            ps.setBigDecimal(9, roomInvoice.getElectricityChargePerUnit());
            ps.setInt(10, roomInvoice.getElectricityUsageUnit());
            ps.setBigDecimal(11, roomInvoice.getElectricityValue());
            ps.setInt(12, CommonWsDb.getIntFromBoolean(roomInvoice.getElectricityUseMinimunUnitCalculate()));
            
            ps.setString(13, roomInvoice.getWaterPreviousMeter());
            ps.setString(14, roomInvoice.getWaterPresentMeter());
            ps.setBigDecimal(15, roomInvoice.getWaterChargePerUnit());
            ps.setInt(16, roomInvoice.getWaterUsageUnit());
            ps.setBigDecimal(17, roomInvoice.getWaterValue());
            ps.setInt(18, CommonWsDb.getIntFromBoolean(roomInvoice.getWaterUseMinimunUnitCalculate()));
            ps.setInt(19, roomInvoice.getStatus());
            ps.setString(20, roomInvoice.getDescription());

            if(roomInvoice.getReceiptId() == null) {
                ps.setNull(21, java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(21, roomInvoice.getReceiptId());
            }

            ps.setString(22, CommonWsDb.getNowDateTimeString());
            
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
                roomInvoice.setRoomPricePerMonth(rs.getBigDecimal("room_price_per_month"));
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
    
    /**
     * 
     * @param roomInvoice
     * @return Boolean
     */
    @Override
    public Boolean cancel(RoomInvoice roomInvoice) {
        Connection con = null;
        PreparedStatement ps = null;
        
        Boolean resultCancel = Boolean.TRUE;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String querysString = "UPDATE room_invoice "
                    + "SET "
                    + "status = ?, " //1
                    + "description = ?, " //2
                    + "updated_date = ? " //3
                    + "WHERE id = ?"; //4
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, 0);
            ps.setString(2, roomInvoice.getDescription());
            ps.setString(3, CommonWsDb.getNowDateTimeString());
            ps.setInt(4, roomInvoice.getId());
            
            Integer effectRow = ps.executeUpdate();
        
            CommonWsDb.optimizeTable(con, ps, "room");

            if (effectRow == 0) {
                resultCancel = Boolean.FALSE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultCancel = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomInvoiceDaoImpl.class.getName());
        }
        
        return resultCancel;
    }
    
}
