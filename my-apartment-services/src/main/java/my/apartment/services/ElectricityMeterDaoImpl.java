package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
}
