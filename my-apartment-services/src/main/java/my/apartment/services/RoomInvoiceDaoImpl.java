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
import my.apartment.model.RoomInvoicePdf;


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
            
            String stringQuery = "SELECT room_invoice.*, room_receipt.receipt_no, "
                    + "building.min_electricity_unit, building.min_electricity_charge,"
                    + "building.min_water_unit, building.min_water_charge " +
                "FROM room_invoice JOIN room ON room_invoice.room_id = room.id " +
                "JOIN building ON room.building_id = building.id " +
                "LEFT JOIN room_receipt ON room_invoice.receipt_id = room_receipt.id "+
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
                
                roomInvoice.setReceiptNo(rs.getString("receipt_no"));
                
                roomInvoice.setMinElectricityUnit(CommonWsUtils.integerZeroToNull(rs.getInt("min_electricity_unit")));
                roomInvoice.setMinElectricityCharge(rs.getBigDecimal("min_electricity_charge"));
                roomInvoice.setMinWaterUnit(CommonWsUtils.integerZeroToNull(rs.getInt("min_water_unit")));
                roomInvoice.setMinWaterCharge(rs.getBigDecimal("min_water_charge"));
                
                
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
            ps.setString(2, CommonWsUtils.strToString(roomInvoice.getDescription()));
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
    
    @Override
    public List<RoomInvoicePdf> getById(Integer roomInvoiceId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomInvoicePdf> roomInvoicePdfs = new ArrayList<RoomInvoicePdf>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT room_invoice.*, building.id AS building_id, "
                    + "building.name AS building_name, building.address AS building_address, building.tel AS building_tel, "
                    + "building.min_electricity_unit AS min_electricity_unit, building.min_electricity_charge AS min_electricity_charge, "
                    + "building.min_water_unit AS min_water_unit, building.min_water_charge AS min_water_charge, "
                    + "room.room_no AS room_no, "
                    + "current_check_in.name AS check_in_name, current_check_in.lastname AS check_in_lastname "
                    
                    + "FROM room_invoice JOIN room ON room_invoice.room_id = room.id "
                    + "JOIN building ON room.building_id = building.id "
                    + "LEFT JOIN current_check_in ON room_invoice.room_id = current_check_in.room_id "
                    
                    + "WHERE room_invoice.id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomInvoiceId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomInvoicePdf roomInvoicePdf = new RoomInvoicePdf();
             
                roomInvoicePdf.setId(rs.getInt("id"));
                roomInvoicePdf.setInvoiceNo(rs.getString("invoice_no"));
                roomInvoicePdf.setInvoiceDate(rs.getDate("invoice_date"));
                roomInvoicePdf.setMonth(rs.getInt("month"));
                roomInvoicePdf.setYear(rs.getInt("year"));
                roomInvoicePdf.setRoomId(rs.getInt("room_id"));
                roomInvoicePdf.setRoomPricePerMonth(rs.getBigDecimal("room_price_per_month"));
                roomInvoicePdf.setElectricityPreviousMeter(rs.getString("electricity_previous_meter"));
                roomInvoicePdf.setElectricityPresentMeter(rs.getString("electricity_present_meter"));
                roomInvoicePdf.setElectricityChargePerUnit(rs.getBigDecimal("electricity_charge_per_unit"));
                roomInvoicePdf.setElectricityUsageUnit(rs.getInt("electricity_usage_unit"));
                roomInvoicePdf.setElectricityValue(rs.getBigDecimal("electricity_value"));
                roomInvoicePdf.setElectricityUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("electricity_use_minimun_unit_calculate")));
                roomInvoicePdf.setWaterPreviousMeter(rs.getString("water_previous_meter"));
                roomInvoicePdf.setWaterPresentMeter(rs.getString("water_present_meter"));
                roomInvoicePdf.setWaterChargePerUnit(rs.getBigDecimal("water_charge_per_unit"));
                roomInvoicePdf.setWaterUsageUnit(rs.getInt("water_usage_unit"));
                roomInvoicePdf.setWaterValue(rs.getBigDecimal("water_value"));
                roomInvoicePdf.setWaterUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("water_use_minimun_unit_calculate")));
                roomInvoicePdf.setStatus(rs.getInt("status"));
                roomInvoicePdf.setDescription(rs.getString("description"));
                roomInvoicePdf.setReceiptId(rs.getInt("receipt_id"));
                roomInvoicePdf.setCreatedDate(rs.getDate("created_date"));
                roomInvoicePdf.setUpdatedDate(rs.getDate("updated_date"));
                
                roomInvoicePdf.setBuildingId(rs.getInt("building_id"));
                roomInvoicePdf.setBuildingName(rs.getString("building_name"));
                roomInvoicePdf.setBuildingAddress(rs.getString("building_address"));
                roomInvoicePdf.setBuildingTel(rs.getString("building_tel"));
                
                roomInvoicePdf.setRoomNo(rs.getString("room_no"));
                
                roomInvoicePdf.setCheckInName(rs.getString("check_in_name"));
                roomInvoicePdf.setCheckInLastname(rs.getString("check_in_lastname"));
                
                roomInvoicePdf.setMinElectricityUnit(rs.getInt("min_electricity_unit"));
                roomInvoicePdf.setMinElectricityCharge(rs.getBigDecimal("min_electricity_charge"));
                
                roomInvoicePdf.setMinWaterUnit(rs.getInt("min_water_unit"));
                roomInvoicePdf.setMinWaterCharge(rs.getBigDecimal("min_water_charge"));
                
                roomInvoicePdfs.add(roomInvoicePdf);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomInvoiceDaoImpl.class.getName());
        }
        
        return roomInvoicePdfs;
        
    }
    
}
