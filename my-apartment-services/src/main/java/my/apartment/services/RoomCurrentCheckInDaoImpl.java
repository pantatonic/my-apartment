package my.apartment.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.apartment.common.Config;
import my.apartment.model.RoomCurrentCheckIn;


public class RoomCurrentCheckInDaoImpl implements RoomCurrentCheckInDao {
    
    @Override
    public List<RoomCurrentCheckIn> getCurrentCheckIn() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomCurrentCheckIn> roomCurrentCheckIns = new ArrayList<RoomCurrentCheckIn>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM current_check_in";
            
            ps = con.prepareStatement(stringQuery);
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
                roomCurrentCheckIn.setNumberCode(rs.getString("number_code"));
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
    
    @Override
    public RoomCurrentCheckIn save(RoomCurrentCheckIn roomCurrentCheckIn) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        RoomCurrentCheckIn roomCurrentCheckInReturn = null;
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String querysString = "";
            
            String processType;
            String insertString = "insert";
            String updateString = "update";
            
            String numberCode = roomCurrentCheckIn.getRoomId() + Long.toString(new Timestamp(System.currentTimeMillis()).getTime());
            
            /** begin delete */
            /*querysString = "DELETE FROM current_check_in WHERE room_id = ?";
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomCurrentCheckIn.getRoomId());*/
            
            //Integer effectRow = ps.executeUpdate();
            /** end delete */
            
            
            /** begin optimize table */
            /*querysString = "OPTIMIZE TABLE current_check_in";
            ps = con.prepareStatement(querysString);
            ps.executeQuery();*/
            /** end optimize table*/
            
            /** begin check process type */
            querysString = "SELECT COUNT(room_id) AS count_row FROM current_check_in WHERE room_id = ?";
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomCurrentCheckIn.getRoomId());
            rs = ps.executeQuery();
            
            rs.first();
            Integer totalRecord = rs.getInt("count_row");
            
            processType = totalRecord == 0 ? insertString : updateString;
            /** end check process type */
            
            
            Integer effectRowProcess;
            
            if(processType.equalsIgnoreCase(insertString)) {
                /** insert process */
                
                querysString = "INSERT INTO current_check_in ("
                        + "room_id, " //1
                        + "check_in_date, " //2
                        + "id_card, " //3
                        + "name, " //4
                        + "lastname, " //5
                        + "address ," //6
                        + "remark, " //7
                        + "number_code, " //8
                        + "created_date) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
                
                ps = con.prepareStatement(querysString);
            
                ps.setInt(1, roomCurrentCheckIn.getRoomId());
                ps.setString(2, roomCurrentCheckIn.getCheckInDateString());
                ps.setString(3, roomCurrentCheckIn.getIdCard());
                ps.setString(4, roomCurrentCheckIn.getName());
                ps.setString(5, roomCurrentCheckIn.getLastname());
                ps.setString(6, roomCurrentCheckIn.getAddress());
                ps.setString(7, roomCurrentCheckIn.getRemark());
                ps.setString(8, numberCode);

                effectRowProcess = ps.executeUpdate();
            }
            else {
                /** update process */
                
                querysString = "UPDATE current_check_in SET "
                        + "check_in_date = ?, " //1
                        + "id_card = ?, " //2
                        + "name = ?, " //3
                        + "lastname = ?, " //4
                        + "address = ?, " //5
                        + "remark = ?, " //6
                        + "number_code = ?, "//7
                        + "updated_date = NOW() "
                        + "WHERE room_id = ? " //8
                        + "";
                
                ps = con.prepareStatement(querysString);
                
                ps.setString(1, roomCurrentCheckIn.getCheckInDateString());
                ps.setString(2, roomCurrentCheckIn.getIdCard());
                ps.setString(3, roomCurrentCheckIn.getName());
                ps.setString(4, roomCurrentCheckIn.getLastname());
                ps.setString(5, roomCurrentCheckIn.getAddress());
                ps.setString(6, roomCurrentCheckIn.getRemark());
                ps.setString(7, roomCurrentCheckIn.getNumberCode());
                ps.setInt(8, roomCurrentCheckIn.getRoomId());
                
                effectRowProcess = ps.executeUpdate();
            }

            if(effectRowProcess != 0) {
                roomCurrentCheckInReturn = new RoomCurrentCheckIn();
                
                roomCurrentCheckInReturn.setRoomId(roomCurrentCheckIn.getRoomId());
                
                if(processType.equalsIgnoreCase(insertString)) {
                    roomCurrentCheckInReturn.setNumberCode(numberCode);
                }
                else {
                    roomCurrentCheckInReturn.setNumberCode(roomCurrentCheckIn.getNumberCode());
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
        
        return roomCurrentCheckInReturn;
    }
    
}
