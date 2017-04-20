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
import my.apartment.model.RoomCurrentCheckIn;


public class RoomCurrentCheckInDaoImpl implements RoomCurrentCheckInDao {
    
    @Override
    public List<RoomCurrentCheckIn> getCurrentByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomCurrentCheckIn> roomCurrentCheckIns = new ArrayList<RoomCurrentCheckIn>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM current_check_in WHERE room_id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomCurrentCheckIn roomCurrentCheckIn = new RoomCurrentCheckIn();
            
                roomCurrentCheckIn.setRoomId(rs.getInt("room_id"));
                roomCurrentCheckIn.setCheckInDate(rs.getDate("check_in_date"));
                roomCurrentCheckIn.setIdCard(rs.getString("id_card"));
                roomCurrentCheckIn.setName(rs.getString("name"));
                roomCurrentCheckIn.setLastname(rs.getString("lastname"));
                roomCurrentCheckIn.setAddress(rs.getString("address"));
                roomCurrentCheckIn.setRemark(rs.getString("remark"));
                roomCurrentCheckIn.setCreatedDate(rs.getDate("created_date"));
                roomCurrentCheckIn.setUpdatedDate(rs.getDate("updated_date"));
                
                roomCurrentCheckIns.add(roomCurrentCheckIn);
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
        
        return roomCurrentCheckIns;
    }
    
}
