package my.apartment.services;

import java.util.List;
import my.apartment.model.WaterMeter;


public interface WaterMeterDao {
    
    public Boolean save(List<WaterMeter> waterMeters);
    
    public List<WaterMeter> getWaterMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year);
    
}
