package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import my.apartment.common.CommonWsDb;
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
                    + "(invoice_id, " //1
                    + "payer, " //2
                    + "status, " //3
                    + "created_date) " //4
                    + "VALUES "
                    + "(?, ?, ?, ?)";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomReceipt.getInvoiceId());
            ps.setString(2, roomReceipt.getPayer());
            ps.setInt(3, roomReceipt.getStatus());
            ps.setString(4, CommonWsDb.getNowDateTimeString());
            
            Integer effectRowProcess = null;
            
            effectRowProcess = ps.executeUpdate();
            
            if(effectRowProcess < 0) {
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
