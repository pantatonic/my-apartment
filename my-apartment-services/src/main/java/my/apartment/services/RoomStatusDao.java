package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomStatus;


public interface RoomStatusDao {
    
    public List<RoomStatus> getAll();
    
}
