package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import my.apartment.common.CommonWsDb;
import my.apartment.model.WaterMeter;


public class WaterMeterDaoImpl implements WaterMeterDao {
    
    @Override
    public Boolean save(List<WaterMeter> waterMeters) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultReturn = Boolean.TRUE;
        
        try {
            con = CommonWsDb.getDbConnection();

            Integer numberOfDataSet = waterMeters.size();
            Integer countSuccess = 0;
            
            for(WaterMeter w : waterMeters) {
                String sqlDeleteExistData = "DELETE FROM water_meter WHERE room_id = ? AND month = ? AND year = ?";
                
                ps = con.prepareStatement(sqlDeleteExistData);
                ps.setInt(1, w.getRoomId());
                ps.setInt(2, w.getMonth());
                ps.setInt(3, w.getYear());
                
                ps.executeUpdate();
                
                CommonWsDb.optimizeTable(con, ps, "water_meter");
                
                String sqlInsert = "INSERT INTO water_meter ("
                        + "room_id, " //1
                        + "month, " //2
                        + "year, " //3
                        + "previous_meter, " //4
                        + "present_meter, " //5
                        + "charge_per_unit, " //6
                        + "usage_unit, " //7
                        + "value, " //8
                        + "use_minimun_unit_calculate, " //9
                        + "created_date)" //10
                        + "VALUES "
                        + "(?, ? ,? ,? ,?, "
                        + "?, ? ,? ,?, ?)";
                
                ps = con.prepareStatement(sqlInsert);
                
                ps.setInt(1, w.getRoomId());
                ps.setInt(2, w.getMonth());
                ps.setInt(3, w.getYear());
                ps.setString(4, w.getPreviousMeter());
                ps.setString(5, w.getPresentMeter());
                ps.setBigDecimal(6, w.getChargePerUnit());
                ps.setInt(7, w.getUsageUnit());
                ps.setBigDecimal(8, w.getValue());
                ps.setInt(9, Objects.equals(w.getUseMinimunUnitCalculate(), Boolean.TRUE) ? 1 : 0);
                ps.setString(10, CommonWsDb.getNowDateTimeString());
                
                Integer effectRowProcess = ps.executeUpdate();
                
                countSuccess = countSuccess + effectRowProcess;
            }
            
            if(countSuccess != numberOfDataSet) {
                resultReturn = Boolean.FALSE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultReturn = Boolean.FALSE;
        }
        finally {
            CommonWsDb.closeFinally(ps, con, WaterMeterDaoImpl.class.getName());
        }
        
        return resultReturn;
    }

    @Override
    public List<WaterMeter> getWaterMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<WaterMeter> waterMetersReturn = new ArrayList<WaterMeter>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT water_meter.* " +
                "FROM water_meter JOIN room ON water_meter.room_id = room.id " +
                "	JOIN building ON room.building_id = building.id " +
                "WHERE building.id = ? " +
                "AND water_meter.month = ? " +
                "AND water_meter.year = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, buildingId);
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
                
                waterMetersReturn.add(waterMeter);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, ElectricityMeterDaoImpl.class.getName());
        }
        
        return waterMetersReturn;
    }
    
}
