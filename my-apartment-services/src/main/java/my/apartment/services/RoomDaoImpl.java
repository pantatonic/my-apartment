package my.apartment.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.apartment.common.Config;
import my.apartment.model.Room;


public class RoomDaoImpl implements RoomDao {
    
    @Override
    public List<Room> getByBuildingId(Integer buildingId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Room> rooms = new ArrayList<Room>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * "
                    + "FROM room JOIN room_status ON room.room_status_id = room_status.id "
                    + "WHERE building_id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, buildingId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Room room = new Room();
                
                room.setId(rs.getInt("id"));
                room.setBuildingId(buildingId);
                room.setFloorSeq(rs.getInt("floor_seq"));
                room.setRoomNo(rs.getString("room_no"));
                room.setName(rs.getString("name"));
                room.setPricePerMonth(rs.getBigDecimal("price_per_month"));
                room.setRoomStatusId(rs.getInt("room_status_id"));
                
                room.setRoomStatusText(rs.getString("status"));
                
                rooms.add(room);
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
        
        return rooms;
    }
    
    @Override
    public List<Room> getById(Integer id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Room> rooms = new ArrayList<Room>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM room WHERE id = ?";

            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                Room room = new Room();
                
                room.setId(rs.getInt("id"));
                room.setBuildingId(rs.getInt("building_id"));
                room.setFloorSeq(rs.getInt("floor_seq"));
                room.setRoomNo(rs.getString("room_no"));
                room.setName(rs.getString("name"));
                room.setPricePerMonth(rs.getBigDecimal("price_per_month"));
                room.setRoomStatusId(rs.getInt("room_status_id"));
                
                rooms.add(room);
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
        
        return rooms;
    }
    
    @Override
    public Room save(Room room) {
        Connection con = null;
        PreparedStatement ps = null;
        
        Room roomReturn = null;
        
        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String querysString = "";
            
            if (room.getId() == null) {
                /** insert process */

                querysString = "INSERT INTO room ("
                        + "building_id, " //1
                        + "floor_seq, " //2
                        + "room_no, " //3
                        + "name, " //4
                        + "price_per_month, " //5
                        + "room_status_id) " //6
                        + "VALUES (?, ?, ?, ?, ?, ?)";
            }
            else {
                /** update process */

                querysString = "UPDATE room SET "
                        + "building_id = ?, " //1
                        + "floor_seq = ?, " //2
                        + "room_no = ?, " //3
                        + "name = ?, " //4
                        + "price_per_month = ?, " //5
                        + "room_status_id = ? " //6
                        + "WHERE id = ?"; //7
            }
            
            ps = con.prepareStatement(querysString, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, room.getBuildingId());
            ps.setInt(2, room.getFloorSeq());
            ps.setString(3, room.getRoomNo());
            ps.setString(4, room.getName());
            ps.setBigDecimal(5, room.getPricePerMonth());
            ps.setInt(6, room.getRoomStatusId());
            
            if(room.getId() != null) {
                /** update process */
                
                ps.setInt(7, room.getId());
            }
            
            Integer effectRow = ps.executeUpdate();
            
            if(effectRow != 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                roomReturn = new Room();
                
                if(room.getId() == null) {
                    /** insert process */
                    
                    if(generatedKeys.next()) {
                        roomReturn.setId(generatedKeys.getInt(1));
                    }
                }
                else {
                    /** update process */
                    
                    roomReturn.setId(room.getId());
                }
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
        
        return roomReturn;
    }
    
}
