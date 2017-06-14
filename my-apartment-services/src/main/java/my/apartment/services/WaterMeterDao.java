package my.apartment.services;

import java.util.List;
import my.apartment.model.WaterMeter;


public interface WaterMeterDao {
    
    public Boolean save(List<WaterMeter> waterMeters);
    
}
