package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import my.apartment.common.CommonUtils;
import my.apartment.common.CommonWsDb;
import my.apartment.common.CommonWsUtils;
import my.apartment.model.RoomReceipt;


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
                            + "status = ? " //2
                            + "WHERE id = ?"; //3
                    
                    ps = con.prepareStatement(stringQuery);
                    
                    ps.setInt(1, generatedKeys.getInt(1));
                    ps.setInt(2, 2); //status is 2 that mean already receipt
                    ps.setInt(3, roomReceipt.getInvoiceId());
                    
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
    
}