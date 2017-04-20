package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomCurrentCheckIn;


public interface RoomCurrentCheckInDao {
    
    public List<RoomCurrentCheckIn> getCurrentByRoomId(Integer roomId);
    
}
