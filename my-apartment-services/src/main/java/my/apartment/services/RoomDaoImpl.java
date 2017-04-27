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
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsDb;
import my.apartment.common.Config;
import my.apartment.model.Room;
import my.apartment.model.RoomCheckInOutHistory;
import my.apartment.model.RoomNoticeCheckOut;


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
                    + "WHERE building_id = ? "
                    + "ORDER BY floor_seq ASC, room_no ASC";
            
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
    
    @Override
    public Boolean checkRoomNoDuplicated(Room room) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Boolean isDuplicated = Boolean.FALSE;
        
        try {
            Class.forName(Config.JDBC_DRIVER);
        
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String aliasFieldCount = "count_row";

            String stringQuery = "SELECT COUNT(id) AS " + aliasFieldCount + " "
                    + "FROM room "
                    + "WHERE room_no = ? " //1
                        + "AND building_id = ? "; //2
            
            if (room.getId() != null) {
                stringQuery += "AND id != ?"; //3
            }
            
            ps = con.prepareStatement(stringQuery);
            ps.setString(1, room.getRoomNo());
            ps.setInt(2, room.getBuildingId());
            
            if (room.getId() != null) {
                ps.setInt(3, room.getId());
            }
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                if(rs.getInt(aliasFieldCount) > 0) {
                    isDuplicated = Boolean.TRUE;
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
        
        return isDuplicated;
    }
    
    @Override
    public Boolean deleteById(Integer roomId) {
        Boolean resultDelete = Boolean.TRUE;
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);

            String querysString = "DELETE FROM room WHERE id = ?";
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomId);
            
            Integer effectRow = ps.executeUpdate();

            if (effectRow == 0) {
                resultDelete = Boolean.FALSE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultDelete = Boolean.FALSE;
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
        
        return resultDelete;
    }
    
    @Override
    public Boolean checkOut(Integer roomId, String numberCode) {
        Boolean resultCheckOut = Boolean.TRUE;
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String querysString;
            
            /** begin remove from current_check_in */
            querysString = "DELETE FROM current_check_in WHERE room_id = ?";
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomId);
            
            ps.executeUpdate();
            
            CommonWsDb.optimizeTable(con, ps, "current_check_in");
            /** end remove from current_check_in */
            
            
            /** begin get data from check in out history */
            querysString = "SELECT * FROM check_in_out_history WHERE room_id = ? AND number_code = ?";
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomId);
            ps.setString(2, numberCode);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                RoomCheckInOutHistory rcioh = new RoomCheckInOutHistory();
                
                rcioh.setRoomId(rs.getInt("room_id"));
                rcioh.setCheckInDateString(rs.getDate("check_in_date").toString());
                rcioh.setCheckOutDateString(CommonWsDb.getNowDateString());
                rcioh.setIdCard(rs.getString("id_card"));
                rcioh.setName(rs.getString("name"));
                rcioh.setLastname(rs.getString("lastname"));
                rcioh.setAddress(rs.getString("address"));
                rcioh.setRemark(rs.getString("remark"));
                rcioh.setNumberCode(rs.getString("number_code"));
                rcioh.setCreatedDateString(CommonWsDb.getNowDateTimeString());
                
                Boolean resultCreateCheckOutRecord = this.createCheckOutRecord(con, ps, rcioh);
                
                if(resultCreateCheckOutRecord == Boolean.FALSE) {
                    resultCheckOut = Boolean.FALSE;
                }
            }
            /** end get data from check in out history */
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultCheckOut = Boolean.FALSE;
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
                
        return resultCheckOut;
    }
    
    private Boolean createCheckOutRecord(Connection con, PreparedStatement ps, RoomCheckInOutHistory rcioh) throws SQLException {
        String queryString = "INSERT INTO check_in_out_history ("
                + "room_id, " //1
                + "check_in_date, " //2
                + "check_out_date, " //3
                + "id_card, " //4
                + "name, " //5
                + "lastname, " //6
                + "address, " //7
                + "remark, " //8
                + "number_code, " //9
                + "created_date) " //10
                + "VALUES ("
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?"
                + ")";
        
        ps = con.prepareStatement(queryString);
        
        ps.setInt(1, rcioh.getRoomId());
        ps.setString(2, rcioh.getCheckInDateString());
        ps.setString(3, rcioh.getCheckOutDateString());
        ps.setString(4, rcioh.getIdCard());
        ps.setString(5, rcioh.getName());
        ps.setString(6, rcioh.getLastname());
        ps.setString(7, rcioh.getAddress());
        ps.setString(8, rcioh.getRemark());
        ps.setString(9, rcioh.getNumberCode());
        ps.setString(10, rcioh.getCreatedDateString());
        
        Integer effectRow = ps.executeUpdate();
        
        if(effectRow > 0) {
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }
    
    @Override
    public List<RoomNoticeCheckOut> getCurrentNoticeCheckOut() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomNoticeCheckOut> roomNoticeCheckOuts = new ArrayList<RoomNoticeCheckOut>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM notice_check_out";
            
            ps = con.prepareStatement(stringQuery);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomNoticeCheckOut roomNoticeCheckOut = new RoomNoticeCheckOut();
                
                roomNoticeCheckOut.setRoomId(rs.getInt("room_id"));
                roomNoticeCheckOut.setNoticeCheckOutDate(rs.getDate("notice_check_out_date"));
                roomNoticeCheckOut.setRemark(rs.getString("remark"));
                roomNoticeCheckOut.setCreatedDate(rs.getDate("created_date"));
                roomNoticeCheckOut.setUpdatedDate(rs.getDate("updated_date"));
                
                roomNoticeCheckOuts.add(roomNoticeCheckOut);
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
        
        return roomNoticeCheckOuts;
    }
    
    @Override
    public List<RoomNoticeCheckOut> getCurrentNoticeCheckOutByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<RoomNoticeCheckOut> roomNoticeCheckOuts = new ArrayList<RoomNoticeCheckOut>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT  * FROM notice_check_out WHERE room_id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomNoticeCheckOut roomNoticeCheckOut = new RoomNoticeCheckOut();
                
                roomNoticeCheckOut.setRoomId(rs.getInt("room_id"));
                roomNoticeCheckOut.setNoticeCheckOutDate(rs.getDate("notice_check_out_date"));
                roomNoticeCheckOut.setRemark(rs.getString("remark"));
                roomNoticeCheckOut.setCreatedDate(rs.getDate("created_date"));
                roomNoticeCheckOut.setUpdatedDate(rs.getDate("updated_date"));
                
                roomNoticeCheckOuts.add(roomNoticeCheckOut);
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
        return roomNoticeCheckOuts;
    }
    
    @Override
    public RoomNoticeCheckOut saveNoticeCheckOut(RoomNoticeCheckOut roomNoticeCheckOut) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        RoomNoticeCheckOut roomNoticeCheckOutReturn = new RoomNoticeCheckOut();
        
        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String querysString = "";
            
            String processType;
            
            /** begin check process type */
            querysString = "SELECT COUNT(room_id) AS count_row FROM notice_check_out WHERE room_id = ?";
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomNoticeCheckOut.getRoomId());
            rs = ps.executeQuery();
            
            rs.first();
            Integer totalRecord = rs.getInt("count_row");
            
            processType = totalRecord == 0 ? CommonString.INSERT_TYPE_STRING : CommonString.UPDATE_TYPE_STRING;
            /** end check process type */
            
            Integer effectRowProcess;
            
            String nowDateString = CommonWsDb.getNowDateTimeString();
            
            if(processType.equalsIgnoreCase(CommonString.INSERT_TYPE_STRING)) {
                /** insert process */
                
                querysString = "INSERT INTO notice_check_out ("
                        + "room_id, " //1
                        + "notice_check_out_date, " //2
                        + "remark, " //3
                        + "created_date) " //4
                        + "VALUES (?, ?, ?, ?)";
                
                ps = con.prepareStatement(querysString);
                
                ps.setInt(1, roomNoticeCheckOut.getRoomId());
                ps.setString(2, roomNoticeCheckOut.getNoticeCheckOutDateString());
                ps.setString(3, roomNoticeCheckOut.getRemark());
                ps.setString(4, nowDateString);
                
                effectRowProcess = ps.executeUpdate();
            }
            else {
                /** update process */
                
                querysString = "UPDATE notice_check_out SET "
                        + "notice_check_out_date = ?, " //1
                        + "remark = ?, " //2
                        + "updated_date = ? "//3
                        + "WHERE room_id = ? " //4
                        + "";
                
                ps = con.prepareStatement(querysString);
                
                ps.setString(1, roomNoticeCheckOut.getNoticeCheckOutDateString());
                ps.setString(2, roomNoticeCheckOut.getRemark());
                ps.setString(3, nowDateString);
                ps.setInt(4, roomNoticeCheckOut.getRoomId());
                
                effectRowProcess = ps.executeUpdate();
            }
            
            if(effectRowProcess != 0) {
                roomNoticeCheckOutReturn = new RoomNoticeCheckOut();
                
                roomNoticeCheckOutReturn.setRoomId(roomNoticeCheckOut.getRoomId());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            
        }
        
        return roomNoticeCheckOutReturn;
    }
    
}
