package my.apartment.services;

import java.util.List;
import my.apartment.model.Room;


public interface RoomDao {
    
    public List<Room> getByBuildingId(Integer buildingId);
    
    public List<Room> getById(Integer roomId);
    
    public Room save(Room room);
    
}
