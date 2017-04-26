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
import my.apartment.common.CommonWsDb;
import my.apartment.common.Config;
import my.apartment.model.RoomCheckInOutHistory;
import my.apartment.model.RoomCurrentCheckIn;


public class RoomCurrentCheckInDaoImpl implements RoomCurrentCheckInDao {
    
    private final String insertString = "insert";
    private final String updateString = "update";
    
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
            
            //System.out.println(CommonWsDb.optimizeTable(con, ps, "current_check_in"));
            /** end optimize table*/

            
            /** begin check process type */
            querysString = "SELECT COUNT(room_id) AS count_row FROM current_check_in WHERE room_id = ?";
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomCurrentCheckIn.getRoomId());
            rs = ps.executeQuery();
            
            rs.first();
            Integer totalRecord = rs.getInt("count_row");
            
            processType = totalRecord == 0 ? this.insertString : this.updateString;
            /** end check process type */
            
            
            Integer effectRowProcess;
            
            RoomCurrentCheckIn toSaveHistory = new RoomCurrentCheckIn();
            
            String nowDateString = CommonWsDb.getNowDateTimeString();
            
            if(processType.equalsIgnoreCase(this.insertString)) {
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
                        + "created_date) " //9
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                ps = con.prepareStatement(querysString);
            
                ps.setInt(1, roomCurrentCheckIn.getRoomId());
                ps.setString(2, roomCurrentCheckIn.getCheckInDateString());
                ps.setString(3, roomCurrentCheckIn.getIdCard());
                ps.setString(4, roomCurrentCheckIn.getName());
                ps.setString(5, roomCurrentCheckIn.getLastname());
                ps.setString(6, roomCurrentCheckIn.getAddress());
                ps.setString(7, roomCurrentCheckIn.getRemark());
                ps.setString(8, numberCode);
                ps.setString(9, nowDateString);

                effectRowProcess = ps.executeUpdate();
                
                toSaveHistory.setRoomId(roomCurrentCheckIn.getRoomId());
                toSaveHistory.setCheckInDateString(roomCurrentCheckIn.getCheckInDateString());
                toSaveHistory.setIdCard(roomCurrentCheckIn.getIdCard());
                toSaveHistory.setName(roomCurrentCheckIn.getName());
                toSaveHistory.setLastname(roomCurrentCheckIn.getLastname());
                toSaveHistory.setAddress(roomCurrentCheckIn.getAddress());
                toSaveHistory.setRemark(roomCurrentCheckIn.getRemark());
                toSaveHistory.setNumberCode(numberCode);
                toSaveHistory.setCreatedDateString(nowDateString);
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
                        + "updated_date = ? "//8
                        + "WHERE room_id = ? " //9
                        + "";
                
                ps = con.prepareStatement(querysString);
                
                ps.setString(1, roomCurrentCheckIn.getCheckInDateString());
                ps.setString(2, roomCurrentCheckIn.getIdCard());
                ps.setString(3, roomCurrentCheckIn.getName());
                ps.setString(4, roomCurrentCheckIn.getLastname());
                ps.setString(5, roomCurrentCheckIn.getAddress());
                ps.setString(6, roomCurrentCheckIn.getRemark());
                ps.setString(7, roomCurrentCheckIn.getNumberCode());
                ps.setString(8, nowDateString);
                ps.setInt(9, roomCurrentCheckIn.getRoomId());
                
                effectRowProcess = ps.executeUpdate();
                
                toSaveHistory.setRoomId(roomCurrentCheckIn.getRoomId());
                toSaveHistory.setCheckInDateString(roomCurrentCheckIn.getCheckInDateString());
                toSaveHistory.setIdCard(roomCurrentCheckIn.getIdCard());
                toSaveHistory.setName(roomCurrentCheckIn.getName());
                toSaveHistory.setLastname(roomCurrentCheckIn.getLastname());
                toSaveHistory.setAddress(roomCurrentCheckIn.getAddress());
                toSaveHistory.setRemark(roomCurrentCheckIn.getRemark());
                toSaveHistory.setNumberCode(roomCurrentCheckIn.getNumberCode());
                toSaveHistory.setUpdatedDateString(nowDateString);
            }

            if(effectRowProcess != 0) {
                roomCurrentCheckInReturn = new RoomCurrentCheckIn();
                
                roomCurrentCheckInReturn.setRoomId(roomCurrentCheckIn.getRoomId());
                
                if(processType.equalsIgnoreCase(this.insertString)) {
                    roomCurrentCheckInReturn.setNumberCode(numberCode);
                }
                else {
                    roomCurrentCheckInReturn.setNumberCode(roomCurrentCheckIn.getNumberCode());
                }
                
                if(!this.saveCheckInOutHistory(con, ps, toSaveHistory, processType)) {
                    roomCurrentCheckInReturn = null;
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
    
    private Boolean saveCheckInOutHistory(
            Connection con, 
            PreparedStatement ps, 
            RoomCurrentCheckIn toSaveHistory, 
            String processType
            ) {
        
        String querysString;
        
        Integer effectRowProcess;
        
        Boolean result = Boolean.TRUE;
        
        if(processType.equalsIgnoreCase(this.insertString)) {
            try {
                querysString = "INSERT INTO check_in_out_history ("
                        + "room_id, " //1
                        + "check_in_date, " //2
                        + "id_card, " //3
                        + "name, " //4
                        + "lastname, " //5
                        + "address ," //6
                        + "remark, " //7
                        + "number_code, " //8
                        + "created_date) " //9
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                ps = con.prepareStatement(querysString);
                
                ps.setInt(1, toSaveHistory.getRoomId());
                ps.setString(2, toSaveHistory.getCheckInDateString());
                ps.setString(3, toSaveHistory.getIdCard());
                ps.setString(4, toSaveHistory.getName());
                ps.setString(5, toSaveHistory.getLastname());
                ps.setString(6, toSaveHistory.getAddress());
                ps.setString(7, toSaveHistory.getRemark());
                ps.setString(8, toSaveHistory.getNumberCode());
                ps.setString(9, toSaveHistory.getCreatedDateString());
                
                effectRowProcess = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(RoomCurrentCheckInDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                
                result = Boolean.FALSE;
            }
        }
        else {
            try {
                querysString = "UPDATE check_in_out_history SET "
                        + "check_in_date = ?, " //1
                        + "id_card = ?, " //2
                        + "name = ?, " //3
                        + "lastname = ?, " //4
                        + "address = ?, " //5
                        + "remark = ?, " //6
                        + "number_code = ?, " //7
                        + "updated_date = ? " //8
                        + "WHERE room_id = ? "  //9
                        + "AND number_code = ?" //10
                        + "";
                
                ps = con.prepareStatement(querysString);
                
                ps.setString(1, toSaveHistory.getCheckInDateString());
                ps.setString(2, toSaveHistory.getIdCard());
                ps.setString(3, toSaveHistory.getName());
                ps.setString(4, toSaveHistory.getLastname());
                ps.setString(5, toSaveHistory.getAddress());
                ps.setString(6, toSaveHistory.getRemark());
                ps.setString(7, toSaveHistory.getNumberCode());
                ps.setString(8, toSaveHistory.getUpdatedDateString());
                ps.setInt(9, toSaveHistory.getRoomId());
                ps.setString(10, toSaveHistory.getNumberCode());
                
                effectRowProcess = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(RoomCurrentCheckInDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                
                result = Boolean.FALSE;
            }
        }
        
        return result;
    }
    
    @Override
    public Object[] getCheckInOutList(Integer roomId, Integer start, Integer length,String searchString) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Object[] objectsReturn = new Object[2];
        
        List<RoomCheckInOutHistory> roomCheckInOutHistorys = new ArrayList<RoomCheckInOutHistory>();
        objectsReturn[0] = roomCheckInOutHistorys;
        
        try {
            Class.forName(Config.JDBC_DRIVER);
        
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT check_in_out_history.*, room.room_no FROM check_in_out_history JOIN room ON check_in_out_history.room_id = room.id WHERE 1=1 AND check_in_out_history.room_id=? ";
            String stringQueryNumRow = "SELECT COUNT(check_in_out_history.id) AS count_row FROM check_in_out_history JOIN room ON check_in_out_history.room_id = room.id WHERE 1=1 AND check_in_out_history.room_id=? ";
            
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
                RoomCheckInOutHistory roomCheckInOutHistory = new RoomCheckInOutHistory();
                
                roomCheckInOutHistory.setId(rs.getInt("id"));
                roomCheckInOutHistory.setRoomId(rs.getInt("room_id"));
                roomCheckInOutHistory.setCheckInDate(rs.getDate("check_in_date"));
                roomCheckInOutHistory.setCheckOutDate(rs.getDate("check_out_date"));
                roomCheckInOutHistory.setIdCard(rs.getString("id_card"));
                roomCheckInOutHistory.setName(rs.getString("name"));
                roomCheckInOutHistory.setLastname(rs.getString("lastname"));
                roomCheckInOutHistory.setAddress(rs.getString("address"));
                roomCheckInOutHistory.setRemark(rs.getString("remark"));
                roomCheckInOutHistory.setNumberCode(rs.getString("number_code"));
                roomCheckInOutHistory.setCreatedDate(rs.getDate("created_date"));
                roomCheckInOutHistory.setUpdatedDate(rs.getDate("updated_date"));
             
                roomCheckInOutHistorys.add(roomCheckInOutHistory);
            }
            /** --------------- */
            
            objectsReturn[0] = roomCheckInOutHistorys;
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
