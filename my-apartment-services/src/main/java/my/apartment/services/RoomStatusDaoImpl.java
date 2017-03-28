package my.apartment.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.apartment.common.Config;
import my.apartment.model.RoomStatus;


public class RoomStatusDaoImpl implements RoomStatusDao {
    
    @Override
    public List<RoomStatus> getAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomStatus> roomStatuses = new ArrayList<RoomStatus>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM room_status";
            
            ps = con.prepareStatement(stringQuery);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomStatus roomStatus = new RoomStatus();
                
                roomStatus.setId(rs.getInt("id"));
                roomStatus.setStatus(rs.getString("status"));
                
                roomStatuses.add(roomStatus);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
           if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        
        return roomStatuses;
    }
    
}
