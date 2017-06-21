package my.apartment.services;

import java.util.List;
import my.apartment.model.ElectricityMeter;


public interface ElectricityMeterDao {
    
    public Boolean save(List<ElectricityMeter> electricityMeters);
    
    public List<ElectricityMeter> getElectricityMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year);
    
}
