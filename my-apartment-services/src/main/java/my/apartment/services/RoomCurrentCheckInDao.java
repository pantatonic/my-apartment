package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomCurrentCheckIn;


public interface RoomCurrentCheckInDao {
    
    public List<RoomCurrentCheckIn> getCurrentCheckIn();
    
    public List<RoomCurrentCheckIn> getCurrentByRoomId(Integer roomId);
    
    public RoomCurrentCheckIn save(RoomCurrentCheckIn roomCurrentCheckIn);
    
    public Object[] getCheckInOutList(Integer roomId, Integer start, Integer length, String searString);
    
}
