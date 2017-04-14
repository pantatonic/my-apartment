package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomReservation;


public interface RoomReservationDao {
    
    public List<RoomReservation> getByRoomId(Integer roomId);
    
}
