package my.apartment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
}
