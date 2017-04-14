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
import my.apartment.model.RoomReservation;


public class RoomReservationDaoImpl implements RoomReservationDao {
    
    @Override
    public List<RoomReservation> getByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM room_reservation WHERE room_id = ? ORDER BY reserve_date DESC";
            System.out.println(stringQuery);
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomReservation roomReservation = new RoomReservation();
                
                roomReservation.setIdCard(rs.getString("id_card"));
                
                roomReservations.add(roomReservation);
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
        
        return roomReservations;
    }
    
}
