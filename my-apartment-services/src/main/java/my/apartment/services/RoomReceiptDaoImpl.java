package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import my.apartment.common.CommonUtils;
import my.apartment.common.CommonWsDb;
import my.apartment.common.CommonWsUtils;
import my.apartment.model.RoomReceipt;
import my.apartment.model.RoomReceiptPdf;


public class RoomReceiptDaoImpl implements RoomReceiptDao {
    
    @Override
    public Boolean create(RoomReceipt roomReceipt) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultSave = Boolean.TRUE;
        
        try {
            con = CommonWsDb.getDbConnection();

            String stringQuery = "INSERT INTO room_receipt "
                    + "(receipt_no, " //1
                    + "invoice_id, " //2
                    + "payer, " //3
                    + "status, " //4
                    + "created_date) " //5
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)";
            
            String receiptNoString = CommonWsUtils.ROOM_RECEIPT_ABBREVIATION
                    + CommonWsUtils.getTimestampString()
                    + CommonUtils.getZeroFillWithNumber(roomReceipt.getInvoiceId(), 4);
            
            ps = con.prepareStatement(stringQuery, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, receiptNoString);            
            ps.setInt(2, roomReceipt.getInvoiceId());
            ps.setString(3, roomReceipt.getPayer());
            ps.setInt(4, roomReceipt.getStatus());
            ps.setString(5, CommonWsDb.getNowDateTimeString());
            
            Integer effectRowProcess = ps.executeUpdate();
            
            if(effectRowProcess != 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                
                if(generatedKeys.next()) {
                    stringQuery = "UPDATE room_invoice "
                            + "SET receipt_id = ?, " //1
                            + "status = ?, " //2
                            + "updated_date = ? " //3
                            + "WHERE id = ?"; //4
                    
                    ps = con.prepareStatement(stringQuery);
                    
                    ps.setInt(1, generatedKeys.getInt(1));
                    ps.setInt(2, 2); //status is 2 that mean already receipt
                    ps.setString(3, CommonWsDb.getNowDateTimeString());
                    ps.setInt(4, roomReceipt.getInvoiceId());
                    
                    ps.executeUpdate();
                }
            }
            else {
                resultSave = Boolean.FALSE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultSave = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomReceiptDaoImpl.class.getName());
        }
        
        return resultSave;
    }
    
    @Override
    public Boolean isAreadyReceiptOfInvoice(Integer roomInvoiceId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultCheck = Boolean.FALSE;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT COUNT(id) AS count_row "
                    + "FROM room_receipt "
                    + "WHERE invoice_id = ? "
                    + "AND status = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomInvoiceId);
            ps.setInt(2, 1);
            
            rs = ps.executeQuery();
            
            rs.first();
            
            Integer totalRecord = rs.getInt("count_row");
            
            if(totalRecord > 0) {
                resultCheck = Boolean.TRUE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomReceiptDaoImpl.class.getName());
        }
        
        return resultCheck;
    }
    
    @Override
    public Boolean cancel(RoomReceipt roomReceipt) {
        Connection con = null;
        PreparedStatement ps = null;

        Boolean resultCancel = Boolean.TRUE;

        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "UPDATE room_receipt "
                    + "SET "
                    + "status = ?, " //1
                    + "description = ?, " //2
                    + "updated_date = ? " //3
                    + "WHERE id = ?"; //4
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, 0); //cancel status is zero
            ps.setString(2, CommonWsUtils.strToString(roomReceipt.getDescription()));
            ps.setString(3, CommonWsDb.getNowDateTimeString());
            ps.setInt(4, roomReceipt.getId());

            Integer effectRow = ps.executeUpdate();
            
            if (effectRow == 0) {
                resultCancel = Boolean.FALSE;
            }
            else {
                stringQuery = "UPDATE room_invoice "
                        + "SET "
                        + "status = ?, " //1
                        + "receipt_id = NULL, "
                        + "updated_date = ? " //2
                        + "WHERE receipt_id = ?"; //3
                
                ps = con.prepareStatement(stringQuery);
                ps.setInt(1, 1); //set room_invoice.status to 1 (unpaid is 1)
                ps.setString(2, CommonWsDb.getNowDateTimeString());
                ps.setInt(3, roomReceipt.getId());
                
                Integer effectRowRoomInvoice = ps.executeUpdate();
                
                if(effectRowRoomInvoice == 0) {
                    resultCancel = Boolean.FALSE;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();

            resultCancel = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomReceiptDaoImpl.class.getName());
        }

        return resultCancel;
    }
    
    @Override
    public List<RoomReceiptPdf> getById(Integer roomReceiptId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<RoomReceiptPdf> roomReceiptPdfs = new ArrayList<RoomReceiptPdf>();

        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT room_receipt.*, " +
                "room_invoice.invoice_no AS invoice_no, " +
                "room_invoice.room_id AS room_id, " +
                "room_invoice.room_price_per_month AS room_price_per_month, " +
                "room_invoice.electricity_previous_meter AS electricity_previous_meter, " +
                "room_invoice.electricity_present_meter AS electricity_present_meter, " +
                "room_invoice.electricity_charge_per_unit AS electricity_charge_per_unit, " +
                "room_invoice.electricity_usage_unit AS electricity_usage_unit, " +
                "room_invoice.electricity_value AS electricity_value, " +
                "room_invoice.electricity_use_minimun_unit_calculate AS electricity_use_minimun_unit_calculate, " +
                "room_invoice.water_previous_meter AS water_previous_meter, " +
                "room_invoice.water_present_meter AS water_present_meter, " +
                "room_invoice.water_charge_per_unit AS water_charge_per_unit, " +
                "room_invoice.water_usage_unit AS water_usage_unit, " +
                "room_invoice.water_value AS water_value, " +
                "room_invoice.water_use_minimun_unit_calculate AS water_use_minimun_unit_calculate, " +
                    
                "building.id AS building_id, " +
                "building.name AS building_name, " +
                "building.address AS building_address, " +
                "building.tel AS building_tel, " +
                    
                "room.room_no AS room_no " +
                    
                "FROM room_receipt JOIN room_invoice ON room_receipt.id = room_invoice.receipt_id " +
                "JOIN building ON room_invoice.room_id = building.id " +
                "JOIN room ON room_invoice.room_id = room.id " +
                    
                "WHERE room_receipt.id = ? " +
                "AND room_invoice.status = 2";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomReceiptId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomReceiptPdf roomReceiptPdf = new RoomReceiptPdf();
                
                roomReceiptPdf.setId(rs.getInt("id"));
                roomReceiptPdf.setReceiptNo(rs.getString("receipt_no"));
                roomReceiptPdf.setInvoiceId(rs.getInt("invoice_id"));
                roomReceiptPdf.setPayer(rs.getString("payer"));
                roomReceiptPdf.setStatus(rs.getInt("status"));
                roomReceiptPdf.setDescription(rs.getString("description"));
                roomReceiptPdf.setCreatedDate(rs.getDate("created_date"));
                roomReceiptPdf.setUpdatedDate(rs.getDate("updated_date"));
                
                roomReceiptPdf.setInvoiceNo(rs.getString("invoice_no"));
                roomReceiptPdf.setRoomId(rs.getInt("room_id"));
                roomReceiptPdf.setRoomPricePerMonth(rs.getBigDecimal("room_price_per_month"));
                roomReceiptPdf.setElectricityPreviousMeter(rs.getString("electricity_previous_meter"));
                roomReceiptPdf.setElectricityPresentMeter(rs.getString("electricity_present_meter"));
                roomReceiptPdf.setElectricityChargePerUnit(rs.getBigDecimal("electricity_charge_per_unit"));
                roomReceiptPdf.setElectricityUsageUnit(rs.getInt("electricity_usage_unit"));
                roomReceiptPdf.setElectricityValue(rs.getBigDecimal("electricity_value"));
                roomReceiptPdf.setElectricityUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("electricity_use_minimun_unit_calculate")));
                roomReceiptPdf.setWaterPreviousMeter(rs.getString("water_previous_meter"));
                roomReceiptPdf.setWaterPresentMeter(rs.getString("water_present_meter"));
                roomReceiptPdf.setWaterChargePerUnit(rs.getBigDecimal("water_charge_per_unit"));
                roomReceiptPdf.setWaterUsageUnit(rs.getInt("water_usage_unit"));
                roomReceiptPdf.setWaterValue(rs.getBigDecimal("water_value"));
                roomReceiptPdf.setWaterUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("water_use_minimun_unit_calculate")));
                
                roomReceiptPdf.setBuildingId(rs.getInt("building_id"));
                roomReceiptPdf.setBuildingName(rs.getString("building_name"));
                roomReceiptPdf.setBuildingAddress(rs.getString("building_address"));
                roomReceiptPdf.setBuildingTel(rs.getString("building_tel"));
                
                roomReceiptPdf.setRoomNo(rs.getString("room_no"));
                
                roomReceiptPdfs.add(roomReceiptPdf);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomInvoiceDaoImpl.class.getName());
        }
        
        return roomReceiptPdfs;
    }
    
}
