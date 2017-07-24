package my.apartment.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsDb;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.Config;
import my.apartment.model.ElectricityMeter;
import my.apartment.model.Room;
import my.apartment.model.RoomCheckInOutHistory;
import my.apartment.model.RoomNoticeCheckOut;
import my.apartment.model.RoomNoticeCheckOutForDashboard;
import my.apartment.model.WaterMeter;


public class RoomDaoImpl implements RoomDao {
    
    /**
     * 
     * @param buildingId
     * @return 
     */
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
                room.setStartupElectricityMeter(rs.getString("startup_electricity_meter"));
                room.setStartupWaterMeter(rs.getString("startup_water_meter"));
                
                room.setRoomStatusText(rs.getString("status"));
                
                rooms.add(room);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return rooms;
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    @Override
    public List<Room> getById(Integer id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Room> rooms = new ArrayList<Room>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT room.*, building.electricity_meter_digit, building.water_meter_digit "
                    + "FROM room JOIN building ON room.building_id = building.id "
                    + "WHERE room.id = ?";

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
                room.setStartupElectricityMeter(rs.getString("startup_electricity_meter"));
                room.setStartupWaterMeter(rs.getString("startup_water_meter"));
                
                room.setElectricityMeterDigit(rs.getInt("electricity_meter_digit"));
                room.setWaterMeterDigit(rs.getInt("water_meter_digit"));
                
                rooms.add(room);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return rooms;
    }
    
    /**
     * 
     * @param room
     * @return 
     */
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
                        + "room_status_id, " //6
                        + "startup_electricity_meter, " //7
                        + "startup_water_meter) " //8
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                
                ps = con.prepareStatement(querysString, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, room.getBuildingId());
                ps.setInt(2, room.getFloorSeq());
                ps.setString(3, room.getRoomNo());
                ps.setString(4, room.getName());
                ps.setBigDecimal(5, room.getPricePerMonth());
                ps.setInt(6, room.getRoomStatusId());
                ps.setString(7, room.getStartupElectricityMeter());
                ps.setString(8, room.getStartupWaterMeter());
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
                
                ps = con.prepareStatement(querysString, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, room.getBuildingId());
                ps.setInt(2, room.getFloorSeq());
                ps.setString(3, room.getRoomNo());
                ps.setString(4, room.getName());
                ps.setBigDecimal(5, room.getPricePerMonth());
                ps.setInt(6, room.getRoomStatusId());
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
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return roomReturn;
    }
    
    /**
     * 
     * @param room
     * @return 
     */
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
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return isDuplicated;
    }
    
    /**
     * 
     * @param roomId
     * @return 
     */
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
            
            CommonWsDb.optimizeTable(con, ps, "room");

            if (effectRow == 0) {
                resultDelete = Boolean.FALSE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultDelete = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return resultDelete;
    }
    
    /**
     * 
     * @param roomId
     * @param numberCode
     * @return 
     */
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
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
                
        return resultCheckOut;
    }
    
    /**
     * 
     * @param con
     * @param ps
     * @param rcioh
     * @return
     * @throws SQLException 
     */
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
    
    /**
     * 
     * @return 
     */
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
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return roomNoticeCheckOuts;
    }
    
    /**
     * 
     * @param roomId
     * @return 
     */
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
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return roomNoticeCheckOuts;
    }
    
    /**
     * 
     * @param roomNoticeCheckOut
     * @return 
     */
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
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return roomNoticeCheckOutReturn;
    }
    
    /**
     * 
     * @param roomId
     * @return 
     */
    @Override
    public Boolean removeNoticeCheckOut(Integer roomId) {
        Boolean resultRemove = Boolean.TRUE;
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String querysString = "DELETE FROM notice_check_out WHERE room_id = ?";
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, roomId);
            
            ps.executeUpdate();
            
            CommonWsDb.optimizeTable(con, ps, "notice_check_out");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultRemove = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return resultRemove;
    }
    
    /**
     * 
     * @param buildingId
     * @param month
     * @param year
     * @return 
     */
    @Override
    public List<ElectricityMeter> getElectricityMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<ElectricityMeter> electricityMeterListReturn = new ArrayList<ElectricityMeter>();

        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            List<Room> roomsByBuildingId = this.getByBuildingId(buildingId);
            
            for(Room room : roomsByBuildingId) {
                ElectricityMeter electricityMeterReturn = new ElectricityMeter();
                
                List<ElectricityMeter> electricityEachRooms = this.getElectricityMeterByRoomIdMonthYear(room.getId(), month, year);
                
                
                electricityMeterReturn.setRoomId(room.getId());
                electricityMeterReturn.setMonth(month);
                electricityMeterReturn.setYear(year);
                
                /** 
                 * if size is zero that mean the specify month doesn't has data  
                 * then call getPreviousElectricityMeterByRoomIdMonthYear to get previous electricity meter
                 */
                if(electricityEachRooms.isEmpty()) {
                    HashMap previousMonthYear = CommonWsUtils.getPreviousMonthYear(month, year);

                    List<ElectricityMeter> electricityEachRoomsFromPrevious 
                            = this.getElectricityMeterByRoomIdMonthYear(room.getId(), 
                                    (Integer) previousMonthYear.get("month"), 
                                    (Integer) previousMonthYear.get("year"));

                    if(electricityEachRoomsFromPrevious.isEmpty()) {
                        /** this scope mean not have all of data for this room, get default meter from table : room */
                        
                        /** put default data into previous of present */
                        electricityMeterReturn.setPreviousMeter(room.getStartupElectricityMeter());
                        
                    }
                    else {
                        /** this scope mean already have data in previous month but not have data in present month  */
                        
                        /** put present of before into previous of present */
                        electricityMeterReturn.setPreviousMeter(electricityEachRoomsFromPrevious.get(0).getPresentMeter());
                    }
                    
                    /** because of this scope not have present data so force null to presetMeter */
                    electricityMeterReturn.setPresentMeter(null);
                    electricityMeterReturn.setChargePerUnit(null);
                    electricityMeterReturn.setUsageUnit(null);
                    electricityMeterReturn.setValue(null);
                    electricityMeterReturn.setUseMinimunUnitCalculate(null);
                    electricityMeterReturn.setCreatedDate(null);
                    electricityMeterReturn.setUpdatedDate(null);                    
                }
                else {
                    /** this scope mean already have data in table "electricity_meter" of this month and year */

                    electricityMeterReturn.setPreviousMeter(electricityEachRooms.get(0).getPreviousMeter());
                    electricityMeterReturn.setPresentMeter(electricityEachRooms.get(0).getPresentMeter());
                    electricityMeterReturn.setChargePerUnit(electricityEachRooms.get(0).getChargePerUnit());
                    electricityMeterReturn.setUsageUnit(electricityEachRooms.get(0).getUsageUnit());
                    electricityMeterReturn.setValue(electricityEachRooms.get(0).getValue());
                    electricityMeterReturn.setUseMinimunUnitCalculate(electricityEachRooms.get(0).getUseMinimunUnitCalculate());
                    electricityMeterReturn.setCreatedDate(electricityEachRooms.get(0).getCreatedDate());
                    electricityMeterReturn.setUpdatedDate(electricityEachRooms.get(0).getUpdatedDate());
                }
                
                electricityMeterListReturn.add(electricityMeterReturn);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return electricityMeterListReturn;
    }
    
    /**
     * 
     * @param roomId
     * @param month
     * @param year
     * @return 
     */
    @Override
    public List<ElectricityMeter> getElectricityMeterByRoomIdMonthYear(Integer roomId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<ElectricityMeter> electricityMeters = new ArrayList<ElectricityMeter>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT * FROM electricity_meter WHERE room_id = ? AND month = ? AND year = ? LIMIT 0, 1";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                ElectricityMeter electricityMeter = new ElectricityMeter();
                
                electricityMeter.setRoomId(rs.getInt("room_id"));
                electricityMeter.setMonth(rs.getInt("month"));
                electricityMeter.setYear(rs.getInt("year"));
                electricityMeter.setPreviousMeter(rs.getString("previous_meter"));
                electricityMeter.setPresentMeter(rs.getString("present_meter"));
                electricityMeter.setChargePerUnit(rs.getBigDecimal("charge_per_unit"));
                electricityMeter.setUsageUnit(rs.getInt("usage_unit"));
                electricityMeter.setValue(rs.getBigDecimal("value"));
                electricityMeter.setUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("use_minimun_unit_calculate")));
                electricityMeter.setCreatedDate(rs.getDate("created_date"));
                electricityMeter.setUpdatedDate(rs.getDate("updated_date"));
                
                electricityMeters.add(electricityMeter);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }

        return electricityMeters;
    }
    
    /**
     * 
     * @param buildingId
     * @param month
     * @param year
     * @return list of model WaterMeter
     */
    @Override
    public List<WaterMeter> getWaterMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<WaterMeter> waterMeterListReturn = new ArrayList<WaterMeter>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            List<Room> roomsByBuildingId = this.getByBuildingId(buildingId);
            
            for(Room room : roomsByBuildingId) {
                WaterMeter waterMeterReturn = new WaterMeter();
                
                List<WaterMeter> waterEachRooms = this.getWaterMeterByRoomIdMonthYear(room.getId(), month, year);
                
                waterMeterReturn.setRoomId(room.getId());
                waterMeterReturn.setMonth(month);
                waterMeterReturn.setYear(year);
                
                /** 
                * if size is zero that mean the specify month doesn't has data  
                * then call getPreviousElectricityMeterByRoomIdMonthYear to get previous electricity meter
                */
                if(waterEachRooms.isEmpty()) {
                    HashMap previousMonthYear = CommonWsUtils.getPreviousMonthYear(month, year);
                    
                    List<WaterMeter> waterEachRoomsFromPrevious 
                            = this.getWaterMeterByRoomIdMonthYear(room.getId(), 
                                    (Integer) previousMonthYear.get("month"), 
                                    (Integer) previousMonthYear.get("year"));
                    
                    if(waterEachRoomsFromPrevious.isEmpty()) {
                        /** this scope mean not have all of data for this room, get default meter from table : room */
                        
                        /** put default data into previous of present */
                        waterMeterReturn.setPreviousMeter(room.getStartupWaterMeter());
                    }
                    else {
                        /** this scope mean already have data in previous month but not have data in present month  */
                        
                        /** put present of before into previous of present */
                        waterMeterReturn.setPreviousMeter(waterEachRoomsFromPrevious.get(0).getPresentMeter());
                    }
                }
                else {
                    /** this scope mean already have data in table "electricity_meter" of this month and year */
                    
                    waterMeterReturn.setPreviousMeter(waterEachRooms.get(0).getPreviousMeter());
                    waterMeterReturn.setPresentMeter(waterEachRooms.get(0).getPresentMeter());
                    waterMeterReturn.setChargePerUnit(waterEachRooms.get(0).getChargePerUnit());
                    waterMeterReturn.setUsageUnit(waterEachRooms.get(0).getUsageUnit());
                    waterMeterReturn.setValue(waterEachRooms.get(0).getValue());
                    waterMeterReturn.setUseMinimunUnitCalculate(waterEachRooms.get(0).getUseMinimunUnitCalculate());
                    waterMeterReturn.setCreatedDate(waterEachRooms.get(0).getCreatedDate());
                    waterMeterReturn.setUpdatedDate(waterEachRooms.get(0).getUpdatedDate());
                }
                
                waterMeterListReturn.add(waterMeterReturn);
           }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return waterMeterListReturn;
    }
    
    /**
     * 
     * @param roomId
     * @param month
     * @param year
     * @return List of model WaterMeter
     */
    @Override
    public List<WaterMeter> getWaterMeterByRoomIdMonthYear(Integer roomId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<WaterMeter> waterMeters = new ArrayList<WaterMeter>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT * FROM water_meter WHERE room_id = ? AND month = ? AND year = ? LIMIT 0, 1";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                WaterMeter waterMeter = new WaterMeter();

                waterMeter.setRoomId(rs.getInt("room_id"));
                waterMeter.setMonth(rs.getInt("month"));
                waterMeter.setYear(rs.getInt("year"));
                waterMeter.setPreviousMeter(rs.getString("previous_meter"));
                waterMeter.setPresentMeter(rs.getString("present_meter"));
                waterMeter.setChargePerUnit(rs.getBigDecimal("charge_per_unit"));
                waterMeter.setUsageUnit(rs.getInt("usage_unit"));
                waterMeter.setValue(rs.getBigDecimal("value"));
                waterMeter.setUseMinimunUnitCalculate(CommonWsDb.getBooleanFromInt(rs.getInt("use_minimun_unit_calculate")));
                waterMeter.setCreatedDate(rs.getDate("created_date"));
                waterMeter.setUpdatedDate(rs.getDate("updated_date"));

                waterMeters.add(waterMeter);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return waterMeters;
    }
    
    /**
     * 
     * @param roomId
     * @return BigDecimal
     */
    @Override
    public BigDecimal getElectricityChargePerUnitByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        BigDecimal electricityChargePerUnitReturn = null;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT building.electricity_charge_per_unit AS electricity_charge_per_unit " +
                "FROM room JOIN building ON room.building_id = building.id " +
                "WHERE room.id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                electricityChargePerUnitReturn = rs.getBigDecimal("electricity_charge_per_unit");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return electricityChargePerUnitReturn;
    }
    
    /**
     * 
     * @param roomId
     * @return Boolean
     */
    @Override
    public Boolean getIsUseElectricityMinimunUnitCalculateByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultReturn = null;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT building.min_electricity_unit AS min_electricity_unit " +
                "FROM room JOIN building ON room.building_id = building.id " +
                "WHERE room.id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                if(rs.getInt("min_electricity_unit") == 0) {
                    resultReturn = Boolean.FALSE;
                }
                else {
                    resultReturn = Boolean.TRUE;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return resultReturn;
    }
    
    /**
     * 
     * @param roomId
     * @return BigDecimal
     */
    @Override
    public BigDecimal getWaterChargePerUnitByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        BigDecimal waterChargePerUnitReturn = null;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT building.water_charge_per_unit AS water_charge_per_unit " +
                "FROM room JOIN building ON room.building_id = building.id " +
                "WHERE room.id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            
            rs = ps.executeQuery();
        
            while(rs.next()) {
                waterChargePerUnitReturn = rs.getBigDecimal("water_charge_per_unit");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return waterChargePerUnitReturn;
    }
    
    
    /**
     * 
     * @param roomId
     * @return Boolean
     */
    @Override
    public Boolean getIsUseWaterMinimunUnitCalculateByRoomId(Integer roomId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultReturn = null;
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT building.min_water_unit AS min_water_unit " +
                "FROM room JOIN building ON room.building_id = building.id " +
                "WHERE room.id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, roomId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                if(rs.getInt("min_water_unit") == 0) {
                    resultReturn = Boolean.FALSE;
                }
                else {
                    resultReturn = Boolean.TRUE;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return resultReturn;
    }
    
    @Override
    public List<RoomNoticeCheckOutForDashboard> getCurrentNoticeCheckOutByBuildingId(Integer buildingId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<RoomNoticeCheckOutForDashboard> rncofds = new ArrayList<RoomNoticeCheckOutForDashboard>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT notice_check_out.*, room.room_no AS room_no, building.id AS building_id, building.name As building_name " +
                "FROM notice_check_out JOIN room on notice_check_out.room_id = room.id " +
                "JOIN building ON room.building_id = building.id " +
                "WHERE building.id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, buildingId);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomNoticeCheckOutForDashboard r = new RoomNoticeCheckOutForDashboard();
                
                r.setRoomId(rs.getInt("room_id"));
                r.setNoticeCheckOutDate(rs.getDate("notice_check_out_date"));
                r.setRemark(rs.getString("remark"));
                r.setCreatedDate(rs.getDate("created_date"));
                r.setUpdatedDate(rs.getDate("updated_date"));
                
                r.setRoomNo(rs.getString("room_no"));
                r.setBuildingId(rs.getInt("building_id"));
                r.setBuildingName(rs.getString("building_name"));
                
                rncofds.add(r);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, RoomDaoImpl.class.getName());
        }
        
        return rncofds;
    }
    
}
