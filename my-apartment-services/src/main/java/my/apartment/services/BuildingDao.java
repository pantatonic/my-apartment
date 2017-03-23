package my.apartment.services;

import java.util.List;
import my.apartment.model.Building;


public interface BuildingDao {
    
    public Building save(Building building);
    
    public List<Building> get();
    
}
