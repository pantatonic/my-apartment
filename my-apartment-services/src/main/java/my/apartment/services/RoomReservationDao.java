package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomReservation;


public interface RoomReservationDao {
    
    public List<RoomReservation> getByRoomId(Integer roomId);
    
    public List<RoomReservation> getCurrentByRoomId(Integer roomId);
    
    public RoomReservation save(RoomReservation roomReservation);
    
}
