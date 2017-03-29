package my.apartment.services;

import java.util.List;
import my.apartment.model.Room;


public interface RoomDao {
    
    List<Room> getByBuildingId(Integer buildingId);
    
    List<Room> getById(Integer roomId);
    
}
