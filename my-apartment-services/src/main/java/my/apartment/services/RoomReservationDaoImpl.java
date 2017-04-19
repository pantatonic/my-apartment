package my.apartment.services;

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
            
            String stringQuery = "SELECT * FROM room_reservation WHERE room_id = ? ORDER BY created_date DESC";

            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomReservation roomReservation = new RoomReservation();
                               
                roomReservation.setId(rs.getInt("id"));
                roomReservation.setReserveDate(rs.getDate("reserve_date"));
                roomReservation.setReserveExpired(rs.getDate("reserve_expired"));
                roomReservation.setRoomId(rs.getInt("room_id"));
                roomReservation.setIdCard(rs.getString("id_card"));
                roomReservation.setReserveName(rs.getString("reserve_name"));
                roomReservation.setReserveLastname(rs.getString("reserve_lastname"));
                roomReservation.setRemark(rs.getString("remark"));
                roomReservation.setCreatedDate(rs.getDate("created_date"));
                roomReservation.setUpdatedDate(rs.getDate("updated_date"));
                roomReservation.setStatus(rs.getInt("status"));
                
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
    
    @Override
    public List<RoomReservation> getCurrentReserve() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM room_reservation WHERE status = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomReservation roomReservation = new RoomReservation();
                               
                roomReservation.setId(rs.getInt("id"));
                roomReservation.setReserveDate(rs.getDate("reserve_date"));
                roomReservation.setReserveExpired(rs.getDate("reserve_expired"));
                roomReservation.setRoomId(rs.getInt("room_id"));
                roomReservation.setIdCard(rs.getString("id_card"));
                roomReservation.setReserveName(rs.getString("reserve_name"));
                roomReservation.setReserveLastname(rs.getString("reserve_lastname"));
                roomReservation.setRemark(rs.getString("remark"));
                roomReservation.setCreatedDate(rs.getDate("created_date"));
                roomReservation.setUpdatedDate(rs.getDate("updated_date"));
                roomReservation.setStatus(rs.getInt("status"));
                
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
    
    @Override
    public List<RoomReservation> getCurrentByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM room_reservation WHERE room_id = ? AND status = ? ORDER BY created_date DESC LIMIT 0,1";

            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            ps.setInt(2, 1);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomReservation roomReservation = new RoomReservation();
                               
                roomReservation.setId(rs.getInt("id"));
                roomReservation.setReserveDate(rs.getDate("reserve_date"));
                roomReservation.setReserveExpired(rs.getDate("reserve_expired"));
                roomReservation.setRoomId(rs.getInt("room_id"));
                roomReservation.setIdCard(rs.getString("id_card"));
                roomReservation.setReserveName(rs.getString("reserve_name"));
                roomReservation.setReserveLastname(rs.getString("reserve_lastname"));
                roomReservation.setRemark(rs.getString("remark"));
                roomReservation.setCreatedDate(rs.getDate("created_date"));
                roomReservation.setUpdatedDate(rs.getDate("updated_date"));
                roomReservation.setStatus(rs.getInt("status"));
                
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
    
    @Override
    public RoomReservation save(RoomReservation roomReservation) {
        Connection con = null;
        PreparedStatement ps = null;
        
        RoomReservation roomReservationReturn = null;

        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "";
            
            if (roomReservation.getId() == null) {
                /** insert process */
                stringQuery = "INSERT INTO room_reservation ("
                        + "reserve_date, " //1
                        + "reserve_expired, " //2
                        + "room_id, "  //3
                        + "id_card, " //4
                        + "reserve_name, " //5
                        + "reserve_lastname, " //6
                        + "remark, " //7
                        + "status, " //8
                        + "created_date)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            }
            else {
                /** update process */

                stringQuery = "UPDATE room_reservation SET "
                        + "reserve_date = ?, " //1
                        + "reserve_expired = ?, " //2
                        + "room_id = ?, " //3
                        + "id_card = ?, " //4
                        + "reserve_name = ?, " //5
                        + "reserve_lastname = ?, " //6
                        + "remark = ?, " //7
                        + "status = ? ,"
                        + "updated_date = NOW() " //8
                        + "WHERE id = ?"; //9
            }

            ps = con.prepareStatement(stringQuery, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, roomReservation.getReserveDateString());
            ps.setString(2, roomReservation.getReserveExpiredString());
            ps.setInt(3, roomReservation.getRoomId());
            ps.setString(4, roomReservation.getIdCard());
            ps.setString(5, roomReservation.getReserveName());
            ps.setString(6, roomReservation.getReserveLastname());
            ps.setString(7, roomReservation.getRemark());
            ps.setInt(8, roomReservation.getStatus());

            if(roomReservation.getId() != null) {
                /** update process */

                ps.setInt(9, roomReservation.getId());
            }

            Integer effectRow = ps.executeUpdate();

            if(effectRow != 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                roomReservationReturn = new RoomReservation();

                if(roomReservation.getId() == null) {
                    /** insert process */

                    if(generatedKeys.next()) {
                        roomReservationReturn.setId(generatedKeys.getInt(1));
                        roomReservationReturn.setRoomId(roomReservation.getRoomId());
                    }
                }
                else {
                    /** update process */
                    
                    roomReservationReturn.setId(roomReservation.getId());
                    roomReservationReturn.setRoomId(roomReservation.getRoomId());
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
                
        return roomReservationReturn;
    }
    
    @Override
    public Object[] getReservationList(Integer roomId, Integer start,Integer length,String searchString) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Object[] objectsReturn = new Object[2];
        
        List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
        objectsReturn[0] = roomReservations;
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM room_reservation WHERE 1=1 AND room_reservation.room_id=? ";
            String stringQueryNumRow = "SELECT COUNT(id) AS count_row FROM room_reservation WHERE 1=1 AND room_reservation.room_id=? ";
            
            if(!searchString.equals("")) {
                
            }
            
            /** get total records */
            stringQueryNumRow += "ORDER BY created_date DESC";
            
            ps = con.prepareStatement(stringQueryNumRow);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            
            rs.first();
            Integer totalRecord = rs.getInt("count_row");
            
            objectsReturn[1] = totalRecord;
            /** ---------------- */
            
            /** get data records */
            stringQuery += "ORDER BY created_date DESC ";
            stringQuery += "LIMIT " + start.toString() + "," + length.toString();
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomReservation roomReservation = new RoomReservation();
                
                roomReservation.setId(rs.getInt("id"));
                roomReservation.setReserveDate(rs.getDate("reserve_date"));
                roomReservation.setReserveExpired(rs.getDate("reserve_expired"));
                roomReservation.setRoomId(rs.getInt("room_id"));
                roomReservation.setIdCard(rs.getString("id_card"));
                roomReservation.setReserveName(rs.getString("reserve_name"));
                roomReservation.setReserveLastname(rs.getString("reserve_lastname"));
                roomReservation.setRemark(rs.getString("remark"));
                roomReservation.setCreatedDate(rs.getDate("created_date"));
                roomReservation.setUpdatedDate(rs.getDate("updated_date"));
                roomReservation.setStatus(rs.getInt("status"));
                
                roomReservations.add(roomReservation);
            }
            /** --------------- */
            
            objectsReturn[0] = roomReservations;
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
        
        return objectsReturn;
    }
    
}
