package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import my.apartment.common.CommonWsDb;
import my.apartment.model.ElectricityMeter;


public class ElectricityMeterDaoImpl implements ElectricityMeterDao {
    
    @Override
    public Boolean save(List<ElectricityMeter> electricityMeters) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Boolean resultReturn = Boolean.TRUE;
        
        try {
            con = CommonWsDb.getDbConnection();

            Integer numberOfDataSet = electricityMeters.size();
            Integer countSuccess = 0;
            
            for(ElectricityMeter e : electricityMeters) {
                String sqlDeleteExistData = "DELETE FROM electricity_meter WHERE room_id = ? AND month = ? AND year = ?";
                
                ps = con.prepareStatement(sqlDeleteExistData);
                ps.setInt(1, e.getRoomId());
                ps.setInt(2, e.getMonth());
                ps.setInt(3, e.getYear());
                
                ps.executeUpdate();
                
                CommonWsDb.optimizeTable(con, ps, "electricity_meter");
                
                String sqlInsert = "INSERT INTO electricity_meter ("
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
                
                ps.setInt(1, e.getRoomId());
                ps.setInt(2, e.getMonth());
                ps.setInt(3, e.getYear());
                ps.setString(4, e.getPreviousMeter());
                ps.setString(5, e.getPresentMeter());
                ps.setBigDecimal(6, e.getChargePerUnit());
                ps.setInt(7, e.getUsageUnit());
                ps.setBigDecimal(8, e.getValue());
                ps.setInt(9, Objects.equals(e.getUseMinimunUnitCalculate(), Boolean.TRUE) ? 1 : 0);
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
            CommonWsDb.closeFinally(ps, con, ElectricityMeterDaoImpl.class.getName());
        }
        
        return resultReturn;
    }
    
    /**
     * 
     * @param buildingId
     * @param month
     * @param year
     * @return List
     */
    @Override
    public List<ElectricityMeter> getElectricityMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ElectricityMeter> electricityMetersReturn = new ArrayList<ElectricityMeter>();
        
        try {
            con = CommonWsDb.getDbConnection();
            
            String stringQuery = "SELECT electricity_meter.* " +
                "FROM electricity_meter JOIN room ON electricity_meter.room_id = room.id " +
                "JOIN building ON room.building_id = building.id " +
                "WHERE building.id = ? " +
                "AND electricity_meter.month = ? " +
                "AND electricity_meter.year = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, buildingId);
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
                
                electricityMetersReturn.add(electricityMeter);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            CommonWsDb.closeFinally(ps, con, ElectricityMeterDaoImpl.class.getName());
        }
        
        return electricityMetersReturn;
    }
}
